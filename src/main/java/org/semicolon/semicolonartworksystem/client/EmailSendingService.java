package org.semicolon.semicolonartworksystem.client;

import lombok.extern.slf4j.Slf4j;
import org.semicolon.semicolonartworksystem.client.dto.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class EmailSendingService {

    @Autowired
    private WebClient emailWebClient;

    public Mono<?> sendEmail(EmailRequest emailRequest){
        return emailWebClient.post()
                .uri("/api/v1/email/enqueue")
                .body(Mono.just(emailRequest), EmailRequest.class)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(response -> {
                    log.info("EmailSending success:: {}", emailRequest);
                })
                .doOnError(throwable -> {
                    log.error(throwable.getMessage());
                });

    }
}
