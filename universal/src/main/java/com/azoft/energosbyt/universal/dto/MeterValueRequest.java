package com.azoft.energosbyt.universal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;

@Data
public class MeterValueRequest {

  private static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

  @NotBlank
  private String system;
  @NotBlank
  private String account;
  @NotBlank
  private String trx_id;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
  private LocalDateTime dateMv;

  /**
   * meter values
   */
  private List<MeterValue> mvs = new ArrayList<>();
}
