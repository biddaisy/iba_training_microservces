package eu.ibagroup.bot.telegram.command;

import com.vdurmont.emoji.EmojiParser;
import eu.ibagroup.bot.command.Command;
import eu.ibagroup.common.mongo.collection.Session;
import eu.ibagroup.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class StatusBotCommand implements BotCommand {

    private final SessionService sessionService;

    @Override
    public Command getCommand() {
        return Command.STATUS;
    }

    @Override
    public String execute(Update update) {
        var chatId = update.getMessage().getChatId();
        Session session = sessionService.getSession(chatId);
        return EmojiParser.parseToUnicode("""
                :information_source: <b>Status of your session</b>
                :black_small_square: Authenticated: <b>%s</b>
                :black_small_square: Name: <b>%s</b>
                :man_technologist: Email: <code>%s</code>
                """.formatted(getConfirmed(session), getName(session), getEmail(session)));
    }

    private static String getEmail(Session session) {
        return session.getEmail() != null ? session.getEmail() : "n/a";
    }

    private static String getName(Session session) {
        return session.getName() != null ? session.getName() : "n/a";
    }

    private static String getConfirmed(Session session) {
        return session.isConfirmed() ? "yes" : "no";
    }

    @Override
    public String getDescription() {
        return "Information about your session";
    }
}
