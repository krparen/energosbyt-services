package com.azoft.energosbyt.prebilling.converter.dto.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class Account {

  private static final String dateFormat = "yyyy-MM-dd";

  private String inform_system;
  private String ext_id;
  private String number;
  private Boolean is_enabled;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat) // отвечает за десериализацию
  @DateTimeFormat(pattern = dateFormat) // отвечает за сериализацию
  private LocalDate begin_date;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat) // отвечает за десериализацию
  @DateTimeFormat(pattern = dateFormat) // отвечает за сериализацию
  private LocalDate end_date;
  private String ext_id_company;
  private String ext_id_division;
  private String ext_id_district;
  private String ext_id_person;
}
