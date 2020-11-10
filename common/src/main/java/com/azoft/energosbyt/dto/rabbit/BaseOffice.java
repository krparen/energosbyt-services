package com.azoft.energosbyt.dto.rabbit;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class BaseOffice implements SystemIdHolder {

    @JsonProperty("system_id")
    String systemId;
    String syncRequestId;
    List<ClDivision> divisions = new ArrayList<>();

    @Data
    public static class ClDivision {
        String division;
        String divBase;
        List<ClBranch> branches = new ArrayList<>();
    }

    @Data
    public static class ClBranch {
        String code;
        String name;
        List<ClOffice> offices = new ArrayList<>();
    }

    @Data
    public static class ClOffice {
        String code;
        String name;
        String address;
    }
}

