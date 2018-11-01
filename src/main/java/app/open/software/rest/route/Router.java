package app.open.software.rest.route;

import app.open.software.rest.handler.MethodMeta;
import app.open.software.rest.header.ResponseHeader;
import app.open.software.rest.header.ResponseHeaders;
import app.open.software.rest.method.HttpMethod;
import app.open.software.rest.parameter.Parameter;
import app.open.software.rest.parameter.PathParam;
import app.open.software.rest.response.ResponseBuilder;
import app.open.software.rest.type.*;
import app.open.software.rest.version.ApiVersion;
import com.google.common.base.Preconditions;
import io.netty.handler.codec.http.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.*;

/**
 * {@link Router} to direct to the right {@link Method} from a {@link URI}
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
public class Router {

	/**
	 * {@link List} of all registered {@link ApiVersion}s
	 */
	private final List<ApiVersion> versionList = new ArrayList<>();

	/**
	 * Register {@link ApiVersion}s
	 *
	 * @param versions {@link ApiVersion}s to register
	 */
	public final Router addVersion(final ApiVersion... versions) {
		this.versionList.addAll(Arrays.asList(versions));
		return this;
	}

	/**
	 * Create the response for the {@link FullHttpRequest}
	 *
	 * @param request Received {@link FullHttpRequest}
	 *
	 * @throws InvocationTargetException An error occurred
	 * @throws IllegalAccessException An error occurred
	 */
	public final FullHttpResponse createResponse(final FullHttpRequest request) throws InvocationTargetException, IllegalAccessException {
		final Optional<ApiVersion> version = this.getVersion(request.uri());

		if (version.isEmpty()) {
			return new ResponseBuilder(request, HttpResponseStatus.NOT_FOUND).getResponse();
		}

		String route = request.uri().replace(version.get().getVersionUri(), "");

		List<Parameter> parameterList = new ArrayList<>();
		if (route.contains("?")) {
			parameterList = this.decodeRoute(route.split("\\?")[1]);
		}

		route = route.split("\\?")[0];

		if (route.endsWith("/") && route.length() > 1) {
			route = route.substring(0, route.length() - 1);
		}

		final Optional<MethodMeta> optionalMethod = this.getMethod(route, HttpMethod.valueOf(request.method().name()), version.get());
		if (optionalMethod.isEmpty()) {
			return new ResponseBuilder(request, HttpResponseStatus.NOT_FOUND).getResponse();
		}

		final Method method = optionalMethod.get().getMethod();

		if (method.isAnnotationPresent(AcceptType.class)) {
			final EncodingType acceptType = method.getAnnotation(AcceptType.class).value();
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

			final Object[] invokeParams = this.getParameter(parameterList, method).toArray();
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
			final ResponseHeader header = method.getAnnotation(ResponseHeader.class);
			builder.setHeader(header.key(), header.value());
		}

		return builder.getResponse();
	}

	/**
	 * @return {@link Optional} of an {@link ApiVersion} filtered by a {@link Route}
	 *
	 * @param route {@link Route} to filter with
	 */
	private Optional<ApiVersion> getVersion(final String route) {
		Preconditions.checkNotNull(route);
		return this.versionList.stream().filter(version -> route.startsWith(version.getVersionUri())).findFirst();
	}

	/**
	 * @return {@link Optional} of a {@link MethodMeta} filtered by a {@link Route} and a {@link HttpMethod}
	 *
	 * @param route {@link Route} to filter with
	 * @param method {@link HttpMethod} to filter with
	 * @param version Searching in this specific {@link ApiVersion}
	 */
	private Optional<MethodMeta> getMethod(final String route, final HttpMethod method, final ApiVersion version) {
		return version.getProvider().getMethodByRoute(route, method);
	}

	/**
	 * @return {@link List} of decoded {@link Parameter} from a {@link Route}
	 *
	 * @param route {@link Route} to decode {@link Parameter}
	 */
	private List<Parameter> decodeRoute(final String route) {
		final ArrayList<Parameter> list = new ArrayList<>();
		Arrays.stream(route.split("&"))
				.map(s -> s.split("="))
				.filter(strings -> strings.length == 2)
				.forEach(parameter -> list.add(new Parameter(parameter[0], parameter[1])));
		return list;
	}

	/**
	 * @return {@link List} of {@link String} parameter for invoking the {@link Method}
	 *
	 * @param queryParameter {@link List} of decoded query {@link Parameter}
	 * @param method {@link Method} to search for parameter
	 */
	private List<String> getParameter(final List<Parameter> queryParameter, final Method method) {
		final List<String> list = new ArrayList<>();
		Arrays.stream(method.getParameters())
				.filter(methodParameter -> methodParameter.isAnnotationPresent(PathParam.class))
				.forEach(methodParameter -> {
					final String parameterName = methodParameter.getAnnotation(PathParam.class).value();
					queryParameter.stream()
							.filter(parameter -> parameter.getName().equals(parameterName))
							.findFirst()
							.map(Parameter::getValue)
							.ifPresent(list::add);
				});
		return list;
	}

}