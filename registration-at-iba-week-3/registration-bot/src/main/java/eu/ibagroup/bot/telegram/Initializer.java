package eu.ibagroup.bot.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@Slf4j
@RequiredArgsConstructor
public class Initializer {

    private final RegistrationBot registrationBot;

    /**
     * Once Spring Boot application fully started, perform the bot registration
     */
    @EventListener(ApplicationReadyEvent.class)
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(registrationBot);
            log.info("Telegram Bot is successfully registered with the Telegram server");
        } catch (TelegramApiRequestException e) {
            log.error("Failed to register bot", e);
            System.exit(1);
        }
    }
}
