package com.azoft.energosbyt.prebilling.converter.converter;

import com.azoft.energosbyt.prebilling.converter.dto.input.BaseOffice;
import com.azoft.energosbyt.prebilling.converter.dto.output.District;
import com.azoft.energosbyt.prebilling.converter.dto.output.Division;
import com.azoft.energosbyt.prebilling.converter.dto.output.ImportDivision;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BaseOfficeToImportDivisionConverter extends AbstractConverter<BaseOffice, ImportDivision> {

    @Override
    public ImportDivision convert(BaseOffice input, Map<String, Object> messageHeaders) {

        String systemCode = getInformSystem(input, messageHeaders);
        List<Division> divisions = new ArrayList<>();

        input.getDivisions().forEach(inputDivision -> {
            inputDivision.getBranches().forEach(branch -> {
                Division division = new Division();
                division.setInform_system(systemCode);
                division.setExt_id(branch.getCode());
                division.setName(branch.getName());
                division.setExt_id_company(inputDivision.getDivBase());
                division.setDistricts(getDistricts(branch.getOffices()));
                divisions.add(division);
            });
        });

        ImportDivision output = new ImportDivision();
        output.setDivisions(divisions);

        return output;
    }

    private List<District> getDistricts(List<BaseOffice.ClOffice> offices) {

        return offices.stream().map(office -> {
            District district = new District();
            district.setExt_id_district(office.getCode());
            district.setName_district(office.getName());
            return district;
        }).collect(Collectors.toList());
    }
}
