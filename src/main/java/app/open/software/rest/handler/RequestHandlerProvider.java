package app.open.software.rest.handler;

import app.open.software.rest.method.HttpMethod;
import app.open.software.rest.route.Route;
import java.util.*;

public class RequestHandlerProvider {

	private final List<RequestHandler> requestHandlerList = new ArrayList<>();

	private final List<MethodMeta> methods = new ArrayList<>();

	public final RequestHandlerProvider add(final RequestHandler... requestHandlers) {
		this.requestHandlerList.addAll(Arrays.asList(requestHandlers));
		this.loadRoutes();
		return this;
	}

	public final List<RequestHandler> getRequestHandlerList() {
		return this.requestHandlerList;
	}

	private void loadRoutes() {
		this.requestHandlerList.forEach(requestHandler -> {
			Arrays.stream(requestHandler.getMethods())
					.filter(method -> method.isAnnotationPresent(Route.class))
					.filter(method -> method.isAnnotationPresent(app.open.software.rest.method.Method.class))
					.filter(method -> method.getReturnType() != Void.class)
					.forEach(method -> {
						this.methods.add(new MethodMeta(
								method.getAnnotation(Route.class).value(),
								method.getAnnotation(app.open.software.rest.method.Method.class).value(),
								requestHandler,
								method));
					});
		});
	}

	public final Optional<MethodMeta> getMethodByRoute(final String route, final HttpMethod method) {
		return this.methods.stream()
				.filter(methodMeta -> methodMeta.getRoute().equals(route))
				.filter(methodMeta -> methodMeta.getHttpMethod().equals(method))
				.findFirst();
	}

}
