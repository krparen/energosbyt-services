package com.azoft.energosbyt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

@Configuration
public class XmlMapperConfig {

  @Bean
  public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter(
      Jackson2ObjectMapperBuilder builder) {
    ObjectMapper mapper = builder.createXmlMapper(true).build();
    mapper.registerModule(new JaxbAnnotationModule());
    ((XmlMapper) mapper).enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);

    return new MappingJackson2XmlHttpMessageConverter(mapper);
  }
}
