package com.azoft.energosbyt.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  @Value("${energosbyt.rabbit.host}")
  private String rabbitHost;
  @Value("${energosbyt.rabbit.port}")
  private int rabbitPort;
  @Value("${energosbyt.rabbit.username}")
  private String rabbitUser;
  @Value("${energosbyt.rabbit.password}")
  private String rabbitPassword;



  @Bean
  public ConnectionFactory connectionFactory() {
    CachingConnectionFactory connectionFactory =
        new CachingConnectionFactory();
    connectionFactory.setHost(rabbitHost);
    connectionFactory.setPort(rabbitPort);
    connectionFactory.setUsername(rabbitUser);
    connectionFactory.setPassword(rabbitPassword);

    return connectionFactory;
  }

  @Bean
  public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }
}
