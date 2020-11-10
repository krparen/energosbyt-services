package com.azoft.energosbyt.prebilling.converter.converter;

import java.util.Map;

public interface Converter<I, O> {
  public O convert (I input, Map<String, Object> messageHeaders);
}
