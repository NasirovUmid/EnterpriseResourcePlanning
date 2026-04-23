package com.pm.EnterpriseResourcePlanning.integrations;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.TelegramResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Slf4j
@Component
public class TelegramIntegration {


    private final String botToken;

    private final String botUsername;

    private final String chatId;

    public TelegramIntegration(@Value("${bot.token}") String botToken, @Value("${bot.username}") String botUsername, @Value("${bot.chatId}") String chatId) {
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.chatId = chatId;
    }

    public void alert(String message) {

        String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";

        WebClient webClient = WebClient.create();

        TelegramResponseDto telegramResponseDto = new TelegramResponseDto(chatId, message);

        webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(telegramResponseDto)
                .retrieve()
                .toBodilessEntity()
                .subscribe(
                        response -> log.info("Successful request: {}", response),
                        error -> log.error("Error: {}", error.getMessage())
                );
    }
}
