package eu.ibagroup.bot.telegram.command;

import eu.ibagroup.bot.command.Command;
import eu.ibagroup.common.service.EventService;
import eu.ibagroup.common.service.RegistrationService;
import eu.ibagroup.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class ExitBotCommand implements BotCommand {

    private final SessionService sessionService;

    private final RegistrationService registrationService;

    private final EventService eventService;

    @Override
    public Command getCommand() {
        return Command.EXIT;
    }

    @Override
    public String execute(Update update) {
        var chatId = update.getMessage().getChatId();
        var registrations = registrationService.getRegistrations(chatId);
        eventService.freeOneSeat(registrations);
        registrationService.deleteRegistrations(chatId);
        sessionService.deleteSession(chatId);
        return "All your data was deleted. Good bye!";
    }

    @Override
    public String getDescription() {
        return "Delete all your data from the application (including existing registrations)";
    }
}
