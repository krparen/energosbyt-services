package com.azoft.energosbyt.prebilling.converter.dto.output;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImportAccount implements InformSystemHolder {
  @JsonProperty("account")
  private List<Account> accounts = new ArrayList<>();

  @JsonIgnore
  @Override
  public String getInformSystem() {

    if (accounts == null || accounts.isEmpty()) {
      return null;
    }

    return accounts.get(0).getInform_system();
  }
}
