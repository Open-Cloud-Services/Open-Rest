package app.open.software.rest.header;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResponseHeaders {

	ResponseHeader[] value();

}
