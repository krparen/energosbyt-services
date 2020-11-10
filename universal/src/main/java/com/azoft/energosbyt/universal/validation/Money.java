package com.azoft.energosbyt.universal.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Проверяет, действительно ли передаваемая сумма больше либо равна нулю
 * и имеет не более двух знаков после запятой
 */
@Documented
@Constraint(validatedBy = MoneyConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Money {
    String message() default "should be positive and should have 0-2 decimal places";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
