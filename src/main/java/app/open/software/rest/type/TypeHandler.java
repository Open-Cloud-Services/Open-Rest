package app.open.software.rest.type;

import java.lang.reflect.Method;

public class TypeHandler {

	public final String handleType(final Method method) {
		if (method.isAnnotationPresent(Type.class)) {
			return method.getAnnotation(Type.class).value().getHeader();
		}
		return ResponseType.UNKNOWN.getHeader();
	}

}
