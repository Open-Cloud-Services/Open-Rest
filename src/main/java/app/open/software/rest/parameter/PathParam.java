package app.open.software.rest.parameter;

import java.lang.annotation.*;

/**
 * Annotation to inject the parameter from the query of the request to the java method parameter
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PathParam {

	/**
	 * @return Name of the java method parameter, because the runtime will not know the parameter name from the source
	 */
	String value();

}
