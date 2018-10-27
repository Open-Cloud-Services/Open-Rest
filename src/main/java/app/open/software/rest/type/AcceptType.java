package app.open.software.rest.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to set the accept type of the request
 *
 * @author x7Airworker
 * @since 1.0
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AcceptType {

	/**
	 * @return The {@link EncodingType} value
	 */
    EncodingType value() default EncodingType.JSON;

}
