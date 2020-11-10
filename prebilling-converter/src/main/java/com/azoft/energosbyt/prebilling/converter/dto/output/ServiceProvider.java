package com.azoft.energosbyt.prebilling.converter.dto.output;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ServiceProvider {
  private String ext_id;
  private String ext_id_service;
  private String ext_id_object;
  private String object_type;
  private String ext_id_account;
  private String ext_id_legal_contract;
  private LocalDateTime begin_date;
}
