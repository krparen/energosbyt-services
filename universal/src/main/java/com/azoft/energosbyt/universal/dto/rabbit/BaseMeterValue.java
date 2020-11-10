package com.azoft.energosbyt.universal.dto.rabbit;


import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class BaseMeterValue {

  private static final String pattern = "dd.MM.yyyy HH:mm:ss";

  String accountNumber;
  String branch;
  String meterNumber;
  String meterId;
  String t1 ;
  String t2;
  String t3;
  String app_id;
  String channel;
  @DateTimeFormat(pattern = pattern)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = pattern)
  LocalDateTime mvDate;
}

