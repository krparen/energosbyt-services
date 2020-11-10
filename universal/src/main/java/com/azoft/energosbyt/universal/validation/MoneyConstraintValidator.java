package com.azoft.energosbyt.universal.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MoneyConstraintValidator implements
        ConstraintValidator<Money, BigDecimal> {

    @Override
    public void initialize(Money constraintAnnotation) {

    }

    @Override
    public boolean isValid(BigDecimal money, ConstraintValidatorContext constraintValidatorContext) {
        return money != null && money.compareTo(BigDecimal.ZERO) >= 0
                && lessOrEqThanTwoDecimalPlaces(money);
    }

    private boolean lessOrEqThanTwoDecimalPlaces(BigDecimal money) {
        return getNumberOfDecimalPlaces(money) <= 2;
    }

    private int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
        return Math.max(0, bigDecimal.stripTrailingZeros().scale());
    }
}
