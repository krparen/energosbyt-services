package com.azoft.energosbyt.dto.rabbit;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class BaseCcbProvider implements SystemIdHolder {
    @JsonProperty("system_id")
    String systemId;
    String syncRequestId;

    List <CLProvider> provider = new ArrayList<>();

    @Data
    public static class CLProvider{
        String ext_id;
        String name;
        String inn;
        String kpp;
        String code;
        String balance_fold;
    }
}

