package org.semicolon.semicolonartworksystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class EmailClient {

    @Value("${EMAIL_SERVICE_BASE_URL}")
    private String baseUrl;

    @Bean("emailWebClient")
    public WebClient webClient(WebClient.Builder builder){
        return builder.baseUrl(baseUrl).build();
    }
}
