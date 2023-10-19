package eu.ibagroup.bot.telegram.command;

import eu.ibagroup.bot.command.Command;
import eu.ibagroup.common.mongo.collection.State;
import eu.ibagroup.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CancelBotCommand implements BotCommand{

    private final SessionService sessionService;

    @Override
    public Command getCommand() {
        return Command.CANCEL;
    }

    @Override
    public String execute(Update update) {
        val chatId = update.getMessage().getChatId();
        val state = sessionService.getBotState(chatId);
        if (state == State.DEFAULT){
            return "No command to cancel";
        } else {
            sessionService.setBotState(chatId, State.DEFAULT);
            return "Command cancelled";
        }
    }

    @Override
    public String getDescription() {
        return "Cancel command";
    }
}
