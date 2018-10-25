package app.open.software.rest.method;

import app.open.software.rest.handler.RequestHandler;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify the methods of an {@link RequestHandler} by the {@link HttpMethod}
 *
 * @author x7Airworker, Tammo0987
 * @since 1.0
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Method {

	/**
	 * @return Value of the {@link HttpMethod}
	 */
    HttpMethod value() default HttpMethod.GET;

}
