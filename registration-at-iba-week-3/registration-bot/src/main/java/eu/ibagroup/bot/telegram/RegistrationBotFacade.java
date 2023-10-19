package eu.ibagroup.bot.telegram;

import eu.ibagroup.bot.config.BotCommandFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class RegistrationBotFacade {

    private final BotCommandFactory commandFactory;

    protected String onUpdateReceived(Update update) {
        return commandFactory.getBotCommand(update).execute(update);
    }
}
