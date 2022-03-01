package it.si2001.rentalcar.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CfValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD})
public @interface CodiceFiscale {

    String message() default "Codice fiscale non valido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}

