package app.open.software.rest.header;

import java.lang.annotation.*;

/**
 * Sending in the response a specific header
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResponseHeader {

	/**
	 * @return Key of the header
	 */
	String key();

	/**
	 * @return Value of the header
	 */
	String value();

}
