package com.azoft.energosbyt.universal.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends RuntimeException {

  private ErrorCode errorCode;
  /**
   * Если true - посылает message из сообщения в ответе как коммент.
   */
  private boolean useMessageAsComment;

  public ApiException(String s, ErrorCode errorCode) {
    super(s);
    this.errorCode = errorCode;
  }

  public ApiException(String s, ErrorCode errorCode, boolean useMessageAsComment) {
    super(s);
    this.errorCode = errorCode;
    this.useMessageAsComment = useMessageAsComment;
  }

  public ApiException(String s, Throwable throwable, ErrorCode errorCode) {
    super(s, throwable);
    this.errorCode = errorCode;
  }

  public ApiException(String s, Throwable throwable, ErrorCode errorCode, boolean useMessageAsComment) {
    super(s, throwable);
    this.errorCode = errorCode;
    this.useMessageAsComment = useMessageAsComment;
  }
}