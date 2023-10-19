package eu.ibagroup.bot.telegram.command;

import eu.ibagroup.bot.command.Command;
import eu.ibagroup.common.mongo.collection.State;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface BotCommand {
    Command getCommand();

    default List<State> getStates() {
        return List.of();
    }

    String execute(Update update);

    String getDescription();
}
