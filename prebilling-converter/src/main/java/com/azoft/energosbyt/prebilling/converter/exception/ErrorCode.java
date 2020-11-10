package com.azoft.energosbyt.prebilling.converter.exception;

import lombok.Getter;

public enum ErrorCode {
  /**
   * Например: Не найден внешний идентификатор <поставщика, услуги и т.д.>, на который ссылается передаваемый объект
   */
  DATA_IS_INVALID(1),
  /**
   * Формат json запроса не соответствует требованиям
   */
  BAD_JSON_REQUEST(3),
  /**
   * Ошибка, возникшая при выполнении операции в самой базе данных. Необработанные ошибки.
   */
  OPERATION_PROCESSING_ERROR(4),
  /**
   * Переданный метод не предусмотрен системной обработки. Проверьте наименование передаваемого метода.
   */
  METHOD_NOT_FOUND(6),
  /**
   * Необходимо корректно заполнить глобальные настройки в ПБ.
   */
  GLOBAL_PROPERTIES_ERROR(7),

  /**
   * Переданные данные не соответствуют общепринятым нормам или правилам в ПБ. Например: номер месяца больше 12 в дате.
   */
  DATA_IS_INCORRECT(8),

  /**
   * Переданный метод не вернул результат выполнения команды.
   */
  NO_RESPONSE(10);
  @Getter
  private int numericValue;

  ErrorCode(int numericValue) {
    this.numericValue = numericValue;
  }
}
