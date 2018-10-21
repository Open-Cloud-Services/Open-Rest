package app.open.software.rest.route;

import app.open.software.rest.handler.MethodMeta;
import app.open.software.rest.header.ResponseHeader;
import app.open.software.rest.header.ResponseHeaders;
import app.open.software.rest.method.HttpMethod;
import app.open.software.rest.parameter.Parameter;
import app.open.software.rest.parameter.PathParam;
import app.open.software.rest.response.ResponseBuilder;
import app.open.software.rest.type.AcceptType;
import app.open.software.rest.type.ResponseType;
import app.open.software.rest.version.ApiVersion;
import com.google.common.base.Preconditions;
import io.netty.handler.codec.http.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Router {

	private final List<ApiVersion> versionList = new ArrayList<>();

	public final Router addVersion(final ApiVersion... versions) {
		this.versionList.addAll(Arrays.asList(versions));
		return this;
	}

	public final FullHttpResponse createResponse(final FullHttpRequest request) throws InvocationTargetException, IllegalAccessException {
		final var version = this.getVersion(request.uri());

		if (version.isEmpty()) {
			return new ResponseBuilder(request, HttpResponseStatus.NOT_FOUND).getResponse();
		}

		var route = request.uri().replace(version.get().getVersionUri(), "");

		List<Parameter> parameterList = new ArrayList<>();
		if (route.contains("?")) {
			parameterList = this.decodeRoute(route.split("\\?")[1]);
		}

		route = route.split("\\?")[0];

		if (route.endsWith("/") && route.length() > 1) {
			route = route.substring(0, route.length() - 1);
		}

		final var optionalMethod = this.getMethod(route, HttpMethod.valueOf(request.method().name()), version.get());
		if (optionalMethod.isEmpty()) {
			return new ResponseBuilder(request, HttpResponseStatus.NOT_FOUND).getResponse();
		}

		final var method = optionalMethod.get().getMethod();

		if (method.isAnnotationPresent(AcceptType.class)) {
			final var acceptType = method.getAnnotation(AcceptType.class).value();
			if (!request.headers().containsValue("Accept", acceptType.getHeader(), true)) {
				return new ResponseBuilder(request, HttpResponseStatus.NOT_ACCEPTABLE).getResponse();
			}
		}

		final ResponseBuilder builder = new ResponseBuilder(request, HttpResponseStatus.OK);

		if (method.isAnnotationPresent(ResponseType.class)) {
			builder.setHeader("Content-Type", method.getAnnotation(ResponseType.class).value().getHeader());
		}

		if (method.getParameterCount() > 0) {
			if (parameterList.size() != method.getParameterCount()) {
				//Arguments are missing
				return new ResponseBuilder(request, HttpResponseStatus.BAD_REQUEST).getResponse();
			}

			final var invokeParams = this.getParameter(parameterList, method).toArray();
			if (invokeParams.length != method.getParameterCount()) {
				throw new IllegalArgumentException("Arguments are not right configured with the @PathParam Annotation");
			}

			builder.writeContent(method.invoke(optionalMethod.get().getRequestHandler(), invokeParams).toString());
		} else {
			builder.writeContent(method.invoke(optionalMethod.get().getRequestHandler()).toString());
		}

		if (method.isAnnotationPresent(ResponseHeaders.class)) {
			Arrays.stream(method.getAnnotation(ResponseHeaders.class).value()).forEach(responseHeader -> {
				builder.setHeader(responseHeader.key(), responseHeader.value());
			});
		}

		if (method.isAnnotationPresent(ResponseHeader.class)) {
			final var header = method.getAnnotation(ResponseHeader.class);
			builder.setHeader(header.key(), header.value());
		}

		System.out.println(method.getName());
		return builder.getResponse();
	}

	private Optional<ApiVersion> getVersion(final String route) {
		Preconditions.checkNotNull(route);
		return this.versionList.stream().filter(version -> route.startsWith(version.getVersionUri())).findFirst();
	}

	//TODO regex
	private Optional<MethodMeta> getMethod(final String route, final HttpMethod method, final ApiVersion version) {
		return version.getProvider().getMethodByRoute(route, method);
	}

	private List<Parameter> decodeRoute(final String route) {
		final var list = new ArrayList<Parameter>();
		Arrays.stream(route.split("&"))
				.map(s -> s.split("="))
				.filter(strings -> strings.length == 2)
				.forEach(parameter -> list.add(new Parameter(parameter[0], parameter[1])));
		return list;
	}

	private List<String> getParameter(final List<Parameter> queryParameter, final Method method) {
		final List<String> list = new ArrayList<>();
		Arrays.stream(method.getParameters())
				.filter(methodParameter -> methodParameter.isAnnotationPresent(PathParam.class))
				.forEach(methodParameter -> {
					final var parameterName = methodParameter.getAnnotation(PathParam.class).value();
					queryParameter.stream()
							.filter(parameter -> parameter.getName().equals(parameterName))
							.findFirst()
							.map(Parameter::getValue)
							.ifPresent(list::add);
				});
		return list;
	}

}
