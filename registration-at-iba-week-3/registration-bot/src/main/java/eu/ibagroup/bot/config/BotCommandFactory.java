package eu.ibagroup.bot.config;


import eu.ibagroup.bot.command.Command;
import eu.ibagroup.bot.telegram.command.BotCommand;
import eu.ibagroup.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class BotCommandFactory {

    private final BotConfiguration botConfiguration;

    private final SessionService sessionService;

    // decide which command the bot needs to execute
    public BotCommand getBotCommand(Update update) {
        // retrieve chat ID
        return getChatId(update)
                .filter(this::isNewChat)
                .map(this::initChat)
                .map(cId -> getHelpBotCommand())
                .or(() -> getBotCommand(getInputText(update)))
                .orElseGet(this::getHelpBotCommand);
    }

    private Optional<BotCommand> getBotCommand(String inputText) {
        return Optional.ofNullable(botConfiguration.getBotCommand(inputText));
    }

    private static String getInputText(Update update) {
        return update.getMessage().getText();
    }

    private BotCommand getHelpBotCommand() {
        return botConfiguration.getBotCommand(Command.HELP);
    }

    private boolean isNewChat(Long chatId) {
        return sessionService.isChatNew(chatId);
    }

    private Long initChat(Long chatId) {
        sessionService.initChat(chatId);
        return chatId;
    }

    private static Optional<Long> getChatId(Update update) {
        return Optional.of(update.getMessage().getChatId());
    }
}
