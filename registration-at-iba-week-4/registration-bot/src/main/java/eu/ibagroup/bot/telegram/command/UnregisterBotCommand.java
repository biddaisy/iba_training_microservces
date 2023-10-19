package eu.ibagroup.bot.telegram.command;

import eu.ibagroup.bot.command.Command;
import eu.ibagroup.common.mongo.collection.Event;
import eu.ibagroup.common.mongo.collection.State;
import eu.ibagroup.common.service.EventService;
import eu.ibagroup.common.service.RegistrationService;
import eu.ibagroup.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UnregisterBotCommand implements BotCommand {

    private final SessionService sessionService;
    private final EventService eventService;
    private final RegistrationService registationService;

    @Override
    public Command getCommand() {
        return Command.UNREGISTER;
    }

    @Override
    public List<State> getStates() {
        return List.of(State.UNREGISTER_ENTER_ID);
    }

    @Override
    public String execute(Update update) {
        val chatId = update.getMessage().getChatId();
        val session = sessionService.getSession(chatId);
        if (!session.isConfirmed()) {
            return "You need to authenticate to use this command";
        }
        val state = session.getState();
        return switch (state) {
            case DEFAULT -> startUnregistration(chatId);
            case UNREGISTER_ENTER_ID -> handleInputEventId(update, chatId);
            default -> cancel(chatId);
        };
    }

    private String cancel(Long chatId) {
        sessionService.setBotState(chatId, State.DEFAULT);
        return "Command cancelled";
    }

    private String handleInputEventId(Update update, Long chatId) {
        val eventId = update.getMessage().getText();
        if (isValid(eventId)) {
            var event = eventService.getEvent(eventId);
            return event
                    .map(e -> unregister(chatId, e))
                    .orElse("Event was not found. Please enter a valid event ID: ");
        } else {
            return "You must enter a valid event ID or cancel the command to exit to the top menu";
        }
    }

    private String unregister(Long chatId, Event event) {
        val eventId = event.getId();
        val registration = registationService.getRegistration(eventId, chatId);
        // check if the user has already registered for this event
        if (registration.isEmpty()) {
            return "You have not registered for this event (%s). Please type event ID you want to unregister:".formatted(event.getName());
        }
        eventService.freeOneSeat(event.getId());
        registationService.deleteRegistration(eventId, chatId);
        sessionService.setBotState(chatId, State.DEFAULT);
        return "You successfully unregistered for '%s' event".formatted(event.getName());
    }

    private static boolean isValid(String eventId) {
        return eventId != null && !eventId.isBlank();
    }

    private String startUnregistration(Long chatId) {
        sessionService.setBotState(chatId, State.UNREGISTER_ENTER_ID);
        return "Please enter event ID you want to unregister for: ";
    }

    @Override
    public String getDescription() {
        return "Delete your registration";
    }

}
