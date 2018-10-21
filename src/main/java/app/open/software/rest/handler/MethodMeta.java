package app.open.software.rest.handler;

import app.open.software.rest.method.HttpMethod;
import java.lang.reflect.Method;

public class MethodMeta {

	private final String route;

	private final HttpMethod httpMethod;

	private final RequestHandler requestHandler;

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
