package com.azoft.energosbyt.dto.rabbit;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

@Data
public class BaseMeterValue {

  private static final String dateFormat = "yyyy-MM-dd";

  String accountNumber;
  String branch;
  String meterNumber;
  String meterId;
  String t1 ;
  String t2;
  String t3;
  String app_id;
  String channel;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat) // отвечает за десериализацию
  Date mvDate;
}
