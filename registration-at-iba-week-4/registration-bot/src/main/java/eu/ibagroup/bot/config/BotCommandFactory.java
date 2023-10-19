package eu.ibagroup.bot.config;

import eu.ibagroup.bot.command.Command;
import eu.ibagroup.bot.telegram.command.BotCommand;
import eu.ibagroup.common.mongo.collection.State;
import eu.ibagroup.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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

        return getChatId(update)
                .filter(this::isNewChat)
                .map(this::initChat)
                .map(cId -> getHelpBotCommand())
                .or(() -> getBotCommandByState(update))
                .map(c -> getBotCommandIfCancel(update).orElse(c))
                .orElseGet(() -> getBotCommandOrHelp(update));
    }

    private BotCommand getBotCommandOrHelp(Update update) {
        return getBotCommandByInputText(update).orElseGet(this::getHelpBotCommand);
    }

    private State getBotState(Update update) {
        return sessionService.getBotState(update.getMessage().getChatId());
    }

    private Optional<BotCommand> getBotCommandIfCancel(Update update) {
        return getBotCommandByInputText(update).filter(c -> c.getCommand() == Command.CANCEL);
    }

    private Optional<BotCommand> getBotCommandByInputText(Update update) {
        val inputText = getInputText(update);
        return Optional.ofNullable(botConfiguration.getBotCommand(inputText));
    }

    private Optional<BotCommand> getBotCommandByState(Update update) {
        val state = getBotState(update);
        return Optional.ofNullable(botConfiguration.getBotCommand(state));
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
