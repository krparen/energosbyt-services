package com.azoft.energosbyt.prebilling.converter.dto.output;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImportMeter implements InformSystemHolder {
  @JsonProperty("meter")
  private List<Meter> meters;

  @JsonIgnore
  @Override
  public String getInformSystem() {

    if (meters == null || meters.isEmpty()) {
      return null;
    }

    return meters.get(0).getInform_system();
  }
}
