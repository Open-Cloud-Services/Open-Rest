package app.open.software.rest.type;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Type {

	ResponseType value() default ResponseType.JSON;

}
