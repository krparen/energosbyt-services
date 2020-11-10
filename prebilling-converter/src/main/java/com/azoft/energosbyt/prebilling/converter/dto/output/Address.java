package com.azoft.energosbyt.prebilling.converter.dto.output;

import lombok.Data;

@Data
public class Address {
  /**
   * Внешний идентификатор
   */
  private String ext_id;
  /**
   * Почтовый индекс
   */
  private String index;
  /**
   * Регион
   */
  private String region;
  /**
   * Район
   */
  private String area;
  private String town_type;
  private String town;
  private String settlement;
  /**
   * Населенный пункт
   */
  private String street_type;
  private String street;
  private String house;
  /**
   * корпус
   */
  private String block;
  private String flat;
  private String address_full;
  private String house_fias;

  private String inform_system;
}
