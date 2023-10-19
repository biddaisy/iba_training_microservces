package eu.ibagroup.bot.telegram;

import eu.ibagroup.bot.config.BotCommandFactory;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class RegistrationBotFacade {

    private final BotCommandFactory commandFactory;

    protected String onUpdateReceived(Update update) {

        val botCommand = commandFactory.getBotCommand(update);

        return botCommand.execute(update);
    }
}
