package app.open.software.rest.type;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResponseType {

	EncodingType value() default EncodingType.JSON;

}
