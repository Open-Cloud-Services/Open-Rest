package app.open.software.rest.handler;

import java.lang.reflect.Method;

public interface RequestHandler {

	default Method[] getMethods() {
		return this.getClass().getDeclaredMethods();
	}

}
