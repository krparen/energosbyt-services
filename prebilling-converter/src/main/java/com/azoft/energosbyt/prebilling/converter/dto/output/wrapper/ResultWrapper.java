package com.azoft.energosbyt.prebilling.converter.dto.output.wrapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Первый слой обёртки над результатом предбиллинга
 */
@Data
public class ResultWrapper<T> {

    private static final String dateFormat = "dd.MM.yyyy HH:mm:ss";

    private UUID guid;
    private String command;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat) // отвечает за десериализацию
    @DateTimeFormat(pattern = dateFormat) // отвечает за сериализацию
    @JsonProperty("creation_date")
    private LocalDateTime creationDate;

    @JsonProperty("inform_system")
    private String informSystem;

    private T data;
}
