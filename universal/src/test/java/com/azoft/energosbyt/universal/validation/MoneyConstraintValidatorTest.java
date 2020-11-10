package com.azoft.energosbyt.universal.validation;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyConstraintValidatorTest {

    private MoneyConstraintValidator validator = new MoneyConstraintValidator();

    @Test
    public void isValid() {
        BigDecimal correct = new BigDecimal("135.84");
        BigDecimal fourDecimals = new BigDecimal("111.333");
        BigDecimal negative = new BigDecimal(-34);

        assertTrue(validator.isValid(correct, null));
        assertFalse(validator.isValid(fourDecimals, null));
        assertFalse(validator.isValid(negative, null));
    }

}