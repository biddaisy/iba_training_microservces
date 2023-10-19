package eu.ibagroup.bot.telegram.command;

import com.vdurmont.emoji.EmojiParser;
import eu.ibagroup.bot.command.Command;
import eu.ibagroup.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class ExitBotCommand implements BotCommand {

    public static final String COMMAND_DESCRIPTION = "Exit";

    private final SessionService sessionService;

    @Override
    public Command getCommand() {
        return Command.EXIT;
    }

    @Override
    public String execute(Update update) {
        val chatId = update.getMessage().getChatId();
        sessionService.deleteSession(chatId);
        return getExitMessage();
    }

    private static String getExitMessage() {
        return EmojiParser.parseToUnicode("All your data has been deleted. :wave: Bye bye\n");
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}
