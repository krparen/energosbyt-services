package com.azoft.energosbyt.exception;

import lombok.Getter;

public enum QiwiResultCode {

  OK(0),

  /**
   * Временная ошибка. Повторите запрос позже/
   */
  TRY_AGAIN_LATER(1),

  /**
   * Неверный формат идентификатора абонента
   */
  WRONG_ABONENT_ID_FORMAT(4),

  /**
   * Идентификатор абонента не найден
   */
  ABONENT_ID_NOT_FOUND(5),

  /**
   *
   * Прием платежа запрещен провайдером/Счет абонента не активен
   * (При отмене платежа – отказ провайдера в отмене платежа)
   */
  PAYMENT_FORBIDDEN_BY_PROVIDER(7),

  /**
   * Прием платежа запрещен по техническим причинам
   */
  PAYMENT_FORBIDDEN_FOR_TECHNICAL_REASONS(8),

  /**
   * Счет абонента не активен
   */
  ABONENT_ACCOUNT_NOT_ACTIVE(79),

  /**
   * Проведение платежа не окончено (при отмене платежа – отмена еще не подтверждена.
   * Система отправит повторный запрос через некоторое время.)
   *
   */
  PAYMENT_PROCESSING_NOT_FINISHED(90),

  /**
   * Сумма слишком мала
   */
  AMOUNT_TOO_SMALL(241),

  /**
   * Сумма слишком велика
   */
  AMOUNT_TOO_BIG(242),

  /**
   * Невозможно проверить состояние счета
   */
  ACCOUNT_UNAVAILABLE(243),

  /**
   * Другая ошибка провайдера
   */
  OTHER_PROVIDER_ERROR(300);

  @Getter
  private final Integer numericCode;

  QiwiResultCode(Integer numericCode) {this.numericCode = numericCode;}
}
