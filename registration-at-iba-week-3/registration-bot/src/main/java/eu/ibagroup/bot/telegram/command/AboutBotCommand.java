package eu.ibagroup.bot.telegram.command;

import com.vdurmont.emoji.EmojiParser;
import eu.ibagroup.bot.command.Command;
import eu.ibagroup.bot.config.BotConfiguration;
import eu.ibagroup.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.net.URL;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AboutBotCommand implements BotCommand {

    private final SessionService sessionService;

    @Value("${registration.web.url}")
    URL url;

    @Autowired
    @Lazy
    BotConfiguration botConfiguration;

    @Override
    public Command getCommand() {
        return Command.ABOUT;
    }

    @Override
    @SneakyThrows
    public String execute(Update update) {
        String webServer = getWebServer();
        val name = botConfiguration.getName();
        val year = LocalDate.now().getYear();
        return EmojiParser.parseToUnicode("""
                :information_source: <b>Information</b>
                :black_small_square: Number of user sessions: <b>%d</b>
                :black_small_square: Web Server: <code>%s</code>
                :man_technologist: Author: <b>@%s</b> © 2021-%d
                """.formatted(sessionService.count(), webServer, name, year));
    }

    private String getWebServer() {
        String protocol = url.getProtocol();
        String server = url.getAuthority();
        String path = url.getPath();
        int lastIndex = getLastIndex(path);
        String context = getContext(path, lastIndex);
        return protocol + "://" + server + getContextDelimiter(context) + context;
    }

    private static String getContext(String path, int lastIndex) {
        return !path.isEmpty() ? path.substring(1, lastIndex) : "";
    }

    private static String getContextDelimiter(String context) {
        return !context.isEmpty() ? "/" : "";
    }

    private static int getLastIndex(String path) {
        int finish = path.indexOf("/", 1);
        return finish >= 0 ? finish : path.length();
    }

    @Override
    public String getDescription() {
        return "Information about the bot";
    }
}
