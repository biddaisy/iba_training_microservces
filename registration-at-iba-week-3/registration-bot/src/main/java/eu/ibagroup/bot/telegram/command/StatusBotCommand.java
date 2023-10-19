package eu.ibagroup.bot.telegram.command;

import com.vdurmont.emoji.EmojiParser;
import eu.ibagroup.bot.command.Command;
import eu.ibagroup.common.mongo.collection.Session;
import eu.ibagroup.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StatusBotCommand implements BotCommand {

    public static final String COMMAND_DESCRIPTION = "Current session info";
    public static final String N_A = "n/a";

    private final SessionService sessionService;

    @Override
    public Command getCommand() {
        return Command.STATUS;
    }

    @Override
    public String execute(Update update) {

        val chatId = update.getMessage().getChatId();
        val session = sessionService.getSession(chatId);
        val confirmed = getConfirmed(session);
        val userName = getUserName(session);
        val userEmail = getUserEmail(session);

        return EmojiParser.parseToUnicode("""
                :information_source: <b>Session status</b>
                :black_small_square: User authenticated: <b>%s</b>
                :black_small_square: User name: <b>%s</b>
                :black_small_square: User email: <b>%s</b>
                """.formatted(confirmed, userName, userEmail));
    }

    private static String getConfirmed(Session session) {
        return session.isConfirmed() ? "yes" : "no";
    }

    private static String getUserEmail(Session session) {
        return Optional.ofNullable(session.getEmail()).orElseGet(() -> N_A);
    }

    private static String getUserName(Session session) {
        return Optional.ofNullable(session.getName()).orElseGet(() -> N_A);
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}
