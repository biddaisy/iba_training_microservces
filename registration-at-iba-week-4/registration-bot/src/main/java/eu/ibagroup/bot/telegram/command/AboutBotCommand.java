package eu.ibagroup.bot.telegram.command;

import com.vdurmont.emoji.EmojiParser;
import eu.ibagroup.bot.command.Command;
import eu.ibagroup.bot.config.BotConfiguration;
import eu.ibagroup.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AboutBotCommand implements BotCommand {

    private final SessionService sessionService;
    @Autowired
    @Lazy
    BotConfiguration botConfiguration;

    private String webServerUrl;

    @Value("${registration.web.url}")
    private void setWebUrl(String webUrl) {
        webServerUrl = getWebServerUrl(webUrl);
    }

    private String getWebServerUrl(String webUrl) {
        return webUrl.substring(0, indexOfSecondSlashEnd(webUrl));
    }

    private static int indexOfSecondSlashEnd(String webUrl) {
        return webUrl.indexOf("/", indexOfFirstSlashEnd(webUrl));
    }

    private static int indexOfFirstSlashEnd(String webUrl) {
        return webUrl.indexOf("/", indexOfDoubleSlashEnd(webUrl)) + 1;
    }

    private static int indexOfDoubleSlashEnd(String webUrl) {
        return webUrl.indexOf("//") + 2;
    }

    @Override
    public Command getCommand() {
        return Command.ABOUT;
    }

    @Override
    public String execute(Update update) {
        var year = LocalDate.now().getYear();
        return EmojiParser.parseToUnicode("""
                :information_source: <b>Information</b>
                :black_small_square: Number of user sessions: <b>%d</b>
                :black_small_square: Web Server: <code>%s</code>
                :man_technologist: Author: <b>@%s</b> © 2021-%d
                """.formatted(sessionService.count(), webServerUrl, botConfiguration.getName(), year));
    }

    @Override
    public String getDescription() {
        return "Information about the bot";
    }
}
