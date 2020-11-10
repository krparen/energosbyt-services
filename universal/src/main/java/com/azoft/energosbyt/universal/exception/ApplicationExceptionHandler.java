package com.azoft.energosbyt.universal.exception;

import com.azoft.energosbyt.universal.dto.BasicResponse;
import com.azoft.energosbyt.universal.dto.OperationStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(OK)
    @ResponseBody
    @ExceptionHandler(ApiException.class)
    public BasicResponse handleApiException(ApiException exception) {
        BasicResponse response = new BasicResponse();
        response.setStatus(OperationStatus.error);
        response.setErrorCode(exception.getErrorCode().getStringValue());
        if (exception.isUseMessageAsComment()) {
            response.setErrorMessage(exception.getMessage());
        }

        return response;
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BasicResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        String errorMessage = fieldErrors.stream()
                .map(this::constructFieldErrorMessage)
                .reduce((s1, s2) -> s1 + "; " + s2)
                .orElse(null);

        BasicResponse response = new BasicResponse();
        response.setStatus(OperationStatus.error);
        response.setErrorCode(ErrorCode.UNEXPECTED_ERROR.getStringValue());
        response.setErrorMessage(errorMessage);

        return response;
    }

    private String constructFieldErrorMessage(FieldError fieldError) {
        return "field " + "'" + fieldError.getField() + "'" + " " + fieldError.getDefaultMessage();
    }


    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public BasicResponse handleUnexpectedException(Exception exception) {
        BasicResponse response = new BasicResponse();
        response.setStatus(OperationStatus.error);
        response.setErrorCode(ErrorCode.UNEXPECTED_ERROR.getStringValue());
        response.setErrorMessage(exception.getMessage());
        return response;
    }
}
