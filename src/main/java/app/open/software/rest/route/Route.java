package app.open.software.rest.route;

import java.lang.annotation.*;
import java.net.URI;

/**
 * Set the {@link URI} for a method
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Route {

	/**
	 * @return Value of the {@link Route}
	 */
	String value() default "/";

}
