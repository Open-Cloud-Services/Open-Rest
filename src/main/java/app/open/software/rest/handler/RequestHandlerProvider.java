package app.open.software.rest.handler;

import app.open.software.rest.method.HttpMethod;
import app.open.software.rest.route.Route;
import java.util.*;

/**
 *
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
public class RequestHandlerProvider {

	/**
	 * {@link List} of all registered {@link RequestHandler}s
	 */
	private final List<RequestHandler> requestHandlerList = new ArrayList<>();

	/**
	 * {@link List} of all registered {@link MethodMeta}s
	 */
	private final List<MethodMeta> methods = new ArrayList<>();

	/**
	 * Register a {@link RequestHandler}
	 *
	 * @param requestHandlers {@link RequestHandler} to register
	 */
	public final RequestHandlerProvider add(final RequestHandler... requestHandlers) {
		this.requestHandlerList.addAll(Arrays.asList(requestHandlers));
		this.loadRoutes();
		return this;
	}

	public final List<RequestHandler> getRequestHandlerList() {
		return this.requestHandlerList;
	}

	/**
	 * Load all {@link Route}s to provide more performance at runtime
	 */
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

	/**
	 * @return {@link Optional} of {@link MethodMeta} to return a {@link MethodMeta} by his route
	 *
	 * @param route Route of the {@link MethodMeta}
	 * @param method {@link HttpMethod} to filter for it
	 */
	public final Optional<MethodMeta> getMethodByRoute(final String route, final HttpMethod method) {
		return this.methods.stream()
				.filter(methodMeta -> methodMeta.getRoute().equals(route))
				.filter(methodMeta -> methodMeta.getHttpMethod().equals(method))
				.findFirst();
	}

}
