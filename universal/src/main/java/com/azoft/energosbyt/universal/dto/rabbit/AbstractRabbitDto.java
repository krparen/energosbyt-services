package com.azoft.energosbyt.universal.dto.rabbit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class AbstractRabbitDto {
    private String error_code;
    private String error_message;
}
