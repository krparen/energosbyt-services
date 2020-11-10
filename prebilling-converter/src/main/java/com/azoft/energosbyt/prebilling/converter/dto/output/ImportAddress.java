package com.azoft.energosbyt.prebilling.converter.dto.output;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImportAddress implements InformSystemHolder {

  @JsonProperty("address")
  private List<Address> addresses = new ArrayList<>();

  @Override
  @JsonIgnore
  public String getInformSystem() {

    if (addresses == null || addresses.isEmpty()) {
      return null;
    }

    return addresses.get(0).getInform_system();
  }
}
