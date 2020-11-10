package com.azoft.energosbyt.prebilling.converter.dto.output;

import lombok.Data;

@Data
public class PointRecord {
  private String inform_system;
  private String ext_id;
  private String ext_id_meter;
  private String name;
  private String place;
  private String transformation_coeff;
  private String ext_id_service;
  private String ext_id_account;
  private String ext_id_legal_contract;
  private String ext_id_address;
}
