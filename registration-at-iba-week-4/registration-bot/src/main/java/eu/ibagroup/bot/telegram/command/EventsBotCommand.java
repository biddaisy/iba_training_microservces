package eu.ibagroup.bot.telegram.command;

import com.vdurmont.emoji.EmojiParser;
import eu.ibagroup.bot.command.Command;
import eu.ibagroup.common.mongo.collection.Event;
import eu.ibagroup.common.service.EventService;
import eu.ibagroup.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static eu.ibagroup.bot.Utils.getEmptyValueCollector;

@Component
@RequiredArgsConstructor
public class EventsBotCommand implements BotCommand {

    private final EventService eventsService;

    private final SessionService sessionService;

    @Override
    public Command getCommand() {
        return Command.EVENTS;
    }

    @Override
    public String execute(Update update) {
        val chatId = update.getMessage().getChatId();
        if (!sessionService.isUserConfirmed(chatId)) {
            return "You need to authenticate to use this command";
        }
        val events = eventsService.getActualEvents();
        val table = events.stream().map(Event::asTelegramString).collect(getEmptyValueCollector("No events available"));
        return EmojiParser.parseToUnicode(table);
    }

    @Override
    public String getDescription() {
        return "List of available events";
    }
}
