package eu.ibagroup.bot.telegram.command;

import eu.ibagroup.bot.command.Command;
import eu.ibagroup.bot.config.MessageConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class HelpBotCommand implements BotCommand {

    private final MessageConfiguration msgConfig;

    @Override
    public Command getCommand() {
        return Command.HELP;
    }

    @Override
    public String execute(Update update) {
        return msgConfig.getHelpMessage();
    }

    @Override
    public String getDescription() {
        return "Helps the bot";
    }

}
