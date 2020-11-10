package com.azoft.energosbyt.universal.dto.rabbit;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BasePremise extends AbstractRabbitDto {
    String premiseId;
    String id;
    String parentPremiseId;
    String premiseType;
    String division;
    String country;
    String postal;
    String state;
    String county;
    String taxVendorGeographicalCode;
    String city;
    String humanSettlement;
    String houseType;
    String street;
    String house;
    String housing;
    String apartment;
    String isInCityLimit;
    //characteristics
    String charTempfias;
    String charPloZem;
    String charPrtOwnr;
    String charGkn;
    String charNeotPl;
    String charMopPl;
    String charDa4a;
    String charSnIznzd;
    String charEststat;
    String charTmpIn;
    String charKolSobs;
    String charVidRasx;
    String charEtaj;
    String charObPloim;
    String charBldYear;
    String charTmpOut;
    String charNomKomn;
    String charGilPl;
    String charOtopPl;
    String charFSobstv;
    String charFlrcount;
    String charSostOb;
    String charObPl;
    String charOktmo;
    String charFias;
    String charKolKomn;
    String charMo;
    String charKolProp;
    String charTPprig;

    String system_id;
    String action ;


    String charDa4A;

}

