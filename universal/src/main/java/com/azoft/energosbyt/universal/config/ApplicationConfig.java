package com.azoft.energosbyt.universal.config;

import com.azoft.energosbyt.service.rabbit.RabbitService;
import com.azoft.energosbyt.universal.service.queue.CcbQueueService;
import com.azoft.energosbyt.universal.service.queue.PayQueueService;
import com.azoft.energosbyt.universal.service.queue.PblQueueService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class ApplicationConfig {

    @Value("${energosbyt.rabbit.request.ccb-queue-name}")
    private String ccbQueueName;
    @Value("${energosbyt.rabbit.request.pay-queue-name}")
    private String payQueueName;
    @Value("${energosbyt.rabbit.request.pbl-queue-name}")
    private String pblQueueName;
    @Value("${energosbyt.application.this-system-id}")
    protected String thisSystemId;


    @Bean
    public CcbQueueService ccbQueueService(RabbitService rabbitService) {
        return new CcbQueueService(ccbQueueName, thisSystemId, rabbitService);
    }

    @Bean
    public PayQueueService payQueueService(RabbitService rabbitService) {
        return new PayQueueService(payQueueName, rabbitService);
    }

    @Bean
    public PblQueueService pblQueueService(RabbitService rabbitService) {
        return new PblQueueService(pblQueueName, rabbitService);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }
}
