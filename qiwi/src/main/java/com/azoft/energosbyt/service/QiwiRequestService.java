package com.azoft.energosbyt.service;

import com.azoft.energosbyt.dto.QiwiRequest;
import com.azoft.energosbyt.dto.QiwiResponse;

public interface QiwiRequestService {

    QiwiResponse process(QiwiRequest qiwiRequest);
}
