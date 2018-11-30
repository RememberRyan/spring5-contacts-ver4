package ee.sdacademy.thymleaf.contacts.validators;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = LastNameCustomValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LastNameCustomConstraint {

    String message() default "Invalid last name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String expectEndsWith();
}
