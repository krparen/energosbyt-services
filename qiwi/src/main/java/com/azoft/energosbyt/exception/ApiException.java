package com.azoft.energosbyt.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends RuntimeException {

  private QiwiResultCode errorCode;
  /**
   * Если true - посылает message из сообщения в ответе как коммент.
   */
  private boolean useMessageAsComment;

  public ApiException(String s, QiwiResultCode errorCode) {
    super(s);
    this.errorCode = errorCode;
  }

  public ApiException(String s, QiwiResultCode errorCode, boolean useMessageAsComment) {
    super(s);
    this.errorCode = errorCode;
    this.useMessageAsComment = useMessageAsComment;
  }

  public ApiException(String s, Throwable throwable, QiwiResultCode errorCode) {
    super(s, throwable);
    this.errorCode = errorCode;
  }

  public ApiException(String s, Throwable throwable, QiwiResultCode errorCode, boolean useMessageAsComment) {
    super(s, throwable);
    this.errorCode = errorCode;
    this.useMessageAsComment = useMessageAsComment;
  }
}
