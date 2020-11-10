package com.azoft.energosbyt.prebilling.converter.dto.output.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Второй слой обёртки над результатом предбиллинга
 * @param <T>
 */
@Data
@AllArgsConstructor
public class ConverterResult<T> {

    private ResultWrapper<T> main;

    public static <U> ConverterResult<U> of(ResultWrapper<U> resultWrapper) {
        return new ConverterResult<>(resultWrapper);
    }
}
