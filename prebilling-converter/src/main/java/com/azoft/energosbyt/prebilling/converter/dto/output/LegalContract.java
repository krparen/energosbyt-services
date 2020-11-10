package com.azoft.energosbyt.prebilling.converter.dto.output;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class LegalContract {
  private String inform_system;
  private String ext_id;
  private String contract_type;
  private String number;
  private String inn;
  private String is_enabled;
  private LocalDateTime begin_date;
  private LocalDateTime end_date;
  private String ext_id_company;
  private String ext_id_division;
  private String ext_id_district;
  private String ext_id_person;
}
