package app.open.software.rest.route;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Route {

	String value() default "/";

}
