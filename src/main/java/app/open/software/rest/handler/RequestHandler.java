package app.open.software.rest.handler;

import java.lang.reflect.Method;

/**
 * Class to handle the rest methods
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
public interface RequestHandler {

	/**
	 * @return All declared {@link Method}s from this class
	 */
	default Method[] getMethods() {
		return this.getClass().getDeclaredMethods();
	}

}
