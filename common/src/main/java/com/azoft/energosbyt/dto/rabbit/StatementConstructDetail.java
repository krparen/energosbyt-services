package com.azoft.energosbyt.dto.rabbit;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Data;

@Data
public class StatementConstructDetail {

  private static final String dateFormat = "yyyy-MM-dd";


  private String constructDetailId;
  private String accountId;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat) // отвечает за десериализацию
  private LocalDate startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat) // отвечает за десериализацию
  private LocalDate endDate;

  private String office;
  private String district;
}
