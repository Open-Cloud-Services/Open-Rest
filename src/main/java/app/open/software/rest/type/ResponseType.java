package app.open.software.rest.type;

import java.lang.annotation.*;

/**
 * Annotation to set the content type of the response
 *
 * @author Tammo0987, x7Airworker
 * @since 1.0
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResponseType {

	/**
	 * @return The {@link EncodingType} value
	 */
	EncodingType value() default EncodingType.JSON;

}
