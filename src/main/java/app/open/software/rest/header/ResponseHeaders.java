package app.open.software.rest.header;

import java.lang.annotation.*;

/**
 * {@link ResponseHeader} container to send more headers
 *
 * @author Tammo0987
 * @since 1.0
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResponseHeaders {

	/**
	 * @return Array of {@link ResponseHeader}s
	 */
	ResponseHeader[] value();

}
