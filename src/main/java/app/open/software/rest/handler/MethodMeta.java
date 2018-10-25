package app.open.software.rest.handler;

import app.open.software.rest.method.HttpMethod;
import java.lang.reflect.Method;
import java.net.URI;

/**
 * Method entity to provide some information about an rest method handler
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
public class MethodMeta {

	/**
	 * {@link URI} route to the method
	 */
	private final String route;

	/**
	 * Type of {@link HttpMethod}
	 */
	private final HttpMethod httpMethod;

	/**
	 * {@link RequestHandler} in which the method is located
	 */
	private final RequestHandler requestHandler;

	/**
	 * The {@link Method} himself
	 */
	private final Method method;

	MethodMeta(final String route, final HttpMethod httpMethod, final RequestHandler requestHandler, final Method method) {
		this.route = route;
		this.httpMethod = httpMethod;
		this.requestHandler = requestHandler;
		this.method = method;
	}

	final String getRoute() {
		return this.route;
	}

	final HttpMethod getHttpMethod() {
		return this.httpMethod;
	}

	public final RequestHandler getRequestHandler() {
		return this.requestHandler;
	}

	public final Method getMethod() {
		return this.method;
	}

}
