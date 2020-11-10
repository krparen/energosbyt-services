package com.azoft.energosbyt.service;

import com.azoft.energosbyt.dto.QiwiRequest;
import com.azoft.energosbyt.dto.QiwiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RabbitRequestService {
  QiwiResponse sendRequestToQueue(QiwiRequest request) throws JsonProcessingException;
}
