package eu.ibagroup.bot.telegram.command;

import eu.ibagroup.bot.command.Command;
import eu.ibagroup.common.mongo.collection.Confirmation;
import eu.ibagroup.common.mongo.collection.Session;
import eu.ibagroup.common.mongo.collection.State;
import eu.ibagroup.common.service.ConfirmationService;
import eu.ibagroup.common.service.SendMailService;
import eu.ibagroup.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticateBotCommand implements BotCommand {

    private final SessionService sessionService;

    private final ConfirmationService confirmationService;

    private final SendMailService sendMailService;

    @Value("${allowed.mail.domains}")
    Set<String> domains;

    @Override
    public Command getCommand() {
        return Command.AUTHENTICATE;
    }

    @Override
    public List<State> getStates() {
        return List.of(State.AUTHENTICATION_ENTER_NAME, State.AUTHENTICATION_ENTER_EMAIL);
    }

    @Override
    public String execute(Update update) {
        val chatId = update.getMessage().getChatId();
        val session = sessionService.getSession(chatId);
        if (session.isConfirmed()) {
            return "You already confirmed your email";
        }
        val state = session.getState();
        return switch (state) {
            case DEFAULT -> startAuthentication(chatId);
            case AUTHENTICATION_ENTER_NAME -> inputUserName(update, chatId);
            case AUTHENTICATION_ENTER_EMAIL -> inputUserEmail(update, chatId);
            default -> cancel(chatId);
        };
    }

    private String cancel(Long chatId) {
        sessionService.setBotState(chatId, State.DEFAULT);
        return "Command cancelled";
    }

    private String inputUserEmail(Update update, Long chatId) {
        val email = update.getMessage().getText();
        if (isValid(email)) {
            handleInputEmail(chatId, email);
            return "You should receive an email soon. Please click the link to confirm your identity. The link will expire after 24 hours.";
        } else {
            return "You must enter your email (valid domains are %session) or cancel the command to exit to the top menu".formatted(domains);
        }
    }

    private void handleInputEmail(Long chatId, String email) {
        sendNotification(chatId, email);
        sessionService.setEmail(chatId, email);
        sessionService.setBotState(chatId, State.DEFAULT);
    }

    private boolean isValid(String email) {
        if (email == null) return false;
        val dogIndex = email.indexOf("@");
        val domain = email.substring(dogIndex + 1);
        return dogIndex > 0 && domains.contains(domain);
    }

    private String inputUserName(Update update, Long chatId) {
        val name = update.getMessage().getText();
        if (name != null && !name.isBlank()) {
            handleInputUserName(chatId, name);
            return "Please enter your email: ";
        } else {
            return "You must enter your name or cancel the command to exit the top menu";
        }
    }

    private void handleInputUserName(Long chatId, String name) {
        sessionService.setBotState(chatId, State.AUTHENTICATION_ENTER_EMAIL);
        sessionService.setName(chatId, name);
    }

    private String startAuthentication(Long chatId) {
        sessionService.setBotState(chatId, State.AUTHENTICATION_ENTER_NAME);
        return "Please enter your name: ";
    }

    @Override
    public String getDescription() {
        return "Authenticate yourself";
    }

    private void sendNotification(Long chatId, String email) {
        val uuid = UUID.randomUUID();
        val confirmation = new Confirmation(uuid.toString(), chatId, LocalDateTime.now(), email);
        confirmationService.createConfirmation(confirmation);
        sendMailService.sendNotification(uuid.toString(), email);
    }
}
