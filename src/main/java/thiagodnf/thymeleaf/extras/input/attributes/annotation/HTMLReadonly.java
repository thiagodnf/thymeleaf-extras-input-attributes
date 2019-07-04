package thiagodnf.thymeleaf.extras.input.attributes.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Payload;

@Documented
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface HTMLReadonly {
    
	String tag() default "readonly";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
