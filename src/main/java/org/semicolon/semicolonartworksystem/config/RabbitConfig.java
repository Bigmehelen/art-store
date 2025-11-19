package org.semicolon.semicolonartworksystem.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EMAIL_QUEUE_NAME = "email_queue";

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE_NAME, true);
    }
}
