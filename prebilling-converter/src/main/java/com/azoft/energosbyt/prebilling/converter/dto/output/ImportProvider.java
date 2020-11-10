package com.azoft.energosbyt.prebilling.converter.dto.output;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImportProvider implements InformSystemHolder {

  @JsonProperty("provider")
  private List<Provider> providers = new ArrayList<>();

  @Override
  @JsonIgnore
  public String getInformSystem() {

    if (providers == null || providers.isEmpty()) {
      return null;
    }

    return providers.get(0).getInform_system();
  }

}
