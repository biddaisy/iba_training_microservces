package eu.ibagroup.bot.telegram;

import eu.ibagroup.bot.config.BotConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationBot extends TelegramLongPollingBot {

    private final BotConfiguration botConfiguration;

    private final RegistrationBotFacade registrationBotFacade;

    @Override
    public String getBotUsername() {
        return botConfiguration.getName();
    }

    @Override
    public String getBotToken() {
        return botConfiguration.getAccessToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        val msg = registrationBotFacade.onUpdateReceived(update);
        sendMessage(update, msg);
    }

    public void sendMessage(Update update, String messageText) {
        if (!update.hasMessage()) return;

        val chatId = update.getMessage().getChatId();

        val message = getMessage(messageText, chatId);
        try {
            execute(message);
        } catch (TelegramApiException telegramApiException) {
            log.error("Failed to send message '{}' to chatId={} because of error {}", messageText, chatId, telegramApiException);
        }
    }

    private static SendMessage getMessage(String messageText, Long chatId) {
        return SendMessage
                .builder().chatId(chatId.toString())
                .parseMode(ParseMode.HTML)
                .text(messageText)
                .build();
    }
}
