package com.azoft.energosbyt.dto;

import lombok.Getter;

public enum Command {
  pay("setPayLkk"),
  check("searchPerson");

  @Getter
  private final String rabbitType;

  Command(String rabbitType) {
    this.rabbitType = rabbitType;
  }
}
