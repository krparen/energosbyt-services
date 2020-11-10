package com.azoft.energosbyt.prebilling.converter.dto.output;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterValue {
  private String ext_id_register;
  private String ext_id;
  private String value;

  public RegisterValue (String ext_id, String value) {
    this.ext_id = ext_id;
    this.value = value;
  }

}
