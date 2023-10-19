package eu.ibagroup.bot.telegram.command;

import com.vdurmont.emoji.EmojiParser;
import eu.ibagroup.bot.command.Command;
import eu.ibagroup.common.mongo.collection.Registration;
import eu.ibagroup.common.service.RegistrationService;
import eu.ibagroup.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static eu.ibagroup.bot.Utils.getEmptyValueCollector;

@Component
@RequiredArgsConstructor
public class RegistrationsBotCommand implements BotCommand {

    private final RegistrationService registrationsService;
    private final SessionService sessionService;

    @Override
    public Command getCommand() {
        return Command.REGISTRATIONS;
    }

    @Override
    public String execute(Update update) {
        val chatId = update.getMessage().getChatId();
        if (!sessionService.isUserConfirmed(chatId)) {
            return "You need to authenticate to use this command";
        }
        val registrations = registrationsService.getRegistrations(chatId);
        val table = registrations.stream()
                .map(Registration::asTelegramString)
                .collect(getEmptyValueCollector("You do not have any registrations"));
        return EmojiParser.parseToUnicode(table);
    }

    @Override
    public String getDescription() {
        return "List your registrations";
    }

}
