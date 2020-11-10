package com.azoft.energosbyt.prebilling.converter.converter;

import com.azoft.energosbyt.dto.rabbit.BaseCcbPremise;
import com.azoft.energosbyt.prebilling.converter.dto.output.Address;
import com.azoft.energosbyt.prebilling.converter.service.ReferenceQueryService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BaseCcbPremiseToAddressConverter extends AbstractConverter<BaseCcbPremise, Address> {

    @Autowired
    private ReferenceQueryService referenceQueryService;

    @Override
    public Address convert(BaseCcbPremise input, Map<String, Object> messageHeaders) {
        Address address = new Address();
        address.setInform_system(getInformSystem(input, messageHeaders));
        address.setExt_id(input.getPremiseId());
        address.setIndex(input.getPostal());
        address.setRegion(referenceQueryService.getRegion(input.getState()));
        address.setArea(input.getCounty());
        address.setTown_type(input.getTaxVendorGeographicalCode());
        address.setTown(input.getCity());
        address.setSettlement(input.getHumanSettlement());
        address.setStreet_type(input.getHouseTypeDesc());
        address.setStreet(input.getStreet());
        address.setHouse(input.getHouse());
        address.setBlock(input.getHousing());
        address.setFlat(input.getApartment());
        address.setAddress_full(getFullAddress(input));
        address.setHouse_fias(input.getCharFias());
        return address;
    }

    private String getFullAddress(BaseCcbPremise input) {

        String address = input.getCounty() + " " + input.getCity() + " " + input.getHumanSettlement() +
                " " + input.getStreet() + " " + input.getHouse();

        if (Strings.isNotBlank(input.getHousing())) {
            address += "/" + input.getHousing();
        }

        if (Strings.isNotBlank(input.getApartment())) {
            address += "-" + input.getApartment();
        }

        return address.replaceAll("null", "").replaceAll(" ", " ");
    }
}
