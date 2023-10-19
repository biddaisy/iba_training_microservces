package eu.ibagroup.bot.telegram.command;

import eu.ibagroup.bot.command.Command;
import eu.ibagroup.common.mongo.collection.Event;
import eu.ibagroup.common.mongo.collection.Registration;
import eu.ibagroup.common.mongo.collection.Session;
import eu.ibagroup.common.mongo.collection.State;
import eu.ibagroup.common.service.EventService;
import eu.ibagroup.common.service.RegistrationService;
import eu.ibagroup.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegisterBotCommand implements BotCommand {

    private final SessionService sessionService;
    private final EventService eventService;
    private final RegistrationService registrationService;

    @Override
    public Command getCommand() {
        return Command.REGISTER;
    }

    @Override
    public List<State> getStates() {
        return List.of(State.REGISTER_ENTER_ID);
    }

    @Override
    public String execute(Update update) {
        val chatId = update.getMessage().getChatId();
        val inputText = update.getMessage().getText();
        val session = sessionService.getSession(chatId);
        if (!session.isConfirmed()) {
            return "You need to authenticate to use this command";
        }
        val state = session.getState();
        return switch (state) {
            case DEFAULT -> startRegistration(chatId);
            case REGISTER_ENTER_ID -> inputEventId(inputText, session);
            default -> cancel(chatId);
        };
    }

    private String cancel(Long chatId) {
        sessionService.setBotState(chatId, State.DEFAULT);
        return "Command cancelled";
    }

    private String inputEventId(String eventId, Session session) {
        if (isValid(eventId)) {
            val event = eventService.getEvent(eventId);
            return event
                    .map(e -> register(session, e))
                    .orElse("Event was not found. Please enter a valid event ID: ");
        } else {
            return "You must enter a valid event ID or cancel the command to exit to the top menu";
        }
    }

    private String register(Session session, Event event) {

        val chatId = session.getChatId();
        val eventId = event.getId();
        // check if not expired
        if (LocalDate.now().isAfter(event.getStartDate())) {
            return "This event has started. Registration is closed. Please select another event ID:";
        }

        // check if the user has already registered for this event
        if (registrationService.getRegistration(eventId, chatId).isPresent()) {
            return "You already registered for this event (%s). Duplicate registration is not allowed. Please select another event ID:".formatted(event.getName());
        }

        // check if there are seats available
        if (event.getCurrentNumOfParticipants() >= event.getMaxNumOfParticipants()) {
            return "There are no seats available for this event. Registration not allowed. Please select another event ID:";
        }

        eventService.reserveOneSeat(event.getId());

        val registration = buildRegistration(chatId, session.getEmail(), event);

        sessionService.setBotState(chatId, State.DEFAULT);
        registrationService.createRegistration(registration);

        return "You successfully registered for '%s' event".formatted(event.getName());
    }

    private static Registration buildRegistration(Long chatId, String email, Event event) {
        return Registration.
                builder().
                id(UUID.randomUUID().toString()).
                eventId(event.getId()).
                eventName(event.getName()).
                eventStartDate(event.getStartDate()).
                email(email).
                chatId(chatId).
                createdDate(LocalDate.now()).
                deleteDate(event.getEndDate()).
                build();
    }

    private static boolean isValid(String eventId) {
        return eventId != null && !eventId.isEmpty();
    }

    private String startRegistration(Long chatId) {
        sessionService.setBotState(chatId, State.REGISTER_ENTER_ID);
        return "Please enter event ID you want to register for: ";
    }

    @Override
    public String getDescription() {
        return "Register for an event";
    }

}
