package eu.ibagroup.bot.telegram.command;

import eu.ibagroup.bot.command.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartBotCommand implements BotCommand {
    @Override
    public Command getCommand() {
        return Command.START;
    }

    @Override
    public String execute(Update update) {
        return "STARTED !";
    }

    @Override
    public String getDescription() {
        return "Starts the bot";
    }
}
