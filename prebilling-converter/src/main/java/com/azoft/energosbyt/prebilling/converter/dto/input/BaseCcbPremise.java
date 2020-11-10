package com.azoft.energosbyt.prebilling.converter.dto.input;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BaseCcbPremise implements SystemIdHolder {
  @JsonProperty("system_id")
  private String systemId;
  private String syncRequestId;
  private String premiseId;
  private String parentPremiseId;
  private String premiseType;
  private String division;
  private String country;
  private String countryDesc;
  private String postal;
  private String state;
  private String stateDesc;
  private String county;
  private String taxVendorGeographicalCode;
  private String city;
  private String humanSettlement;
  private String houseType;
  private String houseTypeDesc;
  private String street;
  private String house;
  private String housing;
  private String apartment;
  private String isInCityLimit;
  private String charTempfias;
  private String charPloZem;
  private String charPrtOwnr;
  private String charGkn;
  private String charNeotPl;
  private String charMopPl;
  private String charDa4a;
  private String charSnIznzd;
  private String charEststat;
  private String charTmpIn;
  private String charKolSobs;
  private String charVidRasx;
  private String charEtaj;
  private String charObPloim;
  private String charBldYear;
  private String charTmpOut;
  private String charNomKomn;
  private String charGilPl;
  private String charOtopPl;
  private String charFSobstv;
  private String charFlrcount;
  private String charSostOb;
  private String charObPl;
  private String charOktmo;
  private String charFias;
  private String charKolKomn;
  private String charMo;
  private String charKolProp;
  private String charTPprig;
}

