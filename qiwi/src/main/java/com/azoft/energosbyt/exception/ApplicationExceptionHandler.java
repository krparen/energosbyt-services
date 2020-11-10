package com.azoft.energosbyt.exception;

import com.azoft.energosbyt.dto.QiwiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<QiwiResponse> handleApiException(ApiException exception) {
        QiwiResponse response = new QiwiResponse();
        response.setResult(exception.getErrorCode().getNumericCode());
        response.setComment("Сообщение на русском для проверки кодировки");
        if (exception.isUseMessageAsComment()) {
            response.setComment(exception.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_XML)
                .body(response);
    }
}
