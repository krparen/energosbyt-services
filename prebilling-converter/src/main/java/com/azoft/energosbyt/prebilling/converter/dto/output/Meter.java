package com.azoft.energosbyt.prebilling.converter.dto.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class Meter {

  private static final String dateFormat = "yyyy-MM-dd";

  private String inform_system;
  private String ext_id;
  private String name;
  private String number;;

  private Integer tariff_zone;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat) // отвечает за десериализацию
  @DateTimeFormat(pattern = dateFormat) // отвечает за сериализацию
  private LocalDate remove_date;

  private List<Register> register;
}
