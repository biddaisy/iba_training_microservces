package eu.ibagroup.bot.config;

import eu.ibagroup.bot.telegram.command.BotCommand;
import eu.ibagroup.bot.command.Command;
import eu.ibagroup.common.mongo.collection.State;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.val;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@ConfigurationProperties("bot")
@Getter
@Setter
@RequiredArgsConstructor
public class BotConfiguration {

    private final MessageConfiguration msgConfig;

    private final ListableBeanFactory beanFactory;

    /**
     * Contains TG bot name picked during bot registration
     */
    private String name;

    /**
     * Contains TG bot token assigned during bot registration
     */
    private String accessToken;

    /**
     * List of supported commands for this bot
     */
    private List<Command> commands;

    private Map<Command, BotCommand> botCommands = new EnumMap<>(Command.class);

    private Map<State, BotCommand> botCommandsByState = new EnumMap<>(State.class);

    private Map<String, BotCommand> botCommandsByString = new HashMap<>();

    private Map<State, Command> commandsByState = new EnumMap<>(State.class);

    @PostConstruct
    public void init() {
        initBotCommands();
        initBotCommandByState();
        initBotCommandsByQualifiedName();
        initHelp();
    }

    private void initHelp() {
        val helpMessage = Stream.of(Command.values()).map(c -> botCommands.get(c)).map(BotConfiguration::getBotCommandInfo).collect(Collectors.joining("\n"));
        msgConfig.setHelpMessage(helpMessage);
    }

    private void initBotCommandsByQualifiedName() {
        botCommandsByString = beanFactory.getBeansOfType(BotCommand.class).values().stream()
                .collect(Collectors.toMap(BotConfiguration::getBotCommandQualifiedName, Function.identity()));
    }

    private static String getBotCommandInfo(BotCommand bc) {
        return getBotCommandQualifiedName(bc) + " -- " + bc.getDescription() + " " + (bc.isAnonymous() ? "" : "(<i>requires authentication</i>)");
    }

    private static String getBotCommandQualifiedName(BotCommand bc) {
        return "/" + bc.getCommand().name().toLowerCase();
    }

    private void initBotCommandByState() {
        botCommandsByState = beanFactory.getBeansOfType(BotCommand.class).values().stream().
                flatMap(BotConfiguration::getStateMap)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Stream<Map.Entry<State, BotCommand>> getStateMap(BotCommand botCommand) {
        return botCommand.getStates().stream().map(state -> Map.entry(state, botCommand));
    }

    private void initBotCommands() {
        botCommands = beanFactory.getBeansOfType(BotCommand.class).values().stream()
                .collect(Collectors.toMap(BotCommand::getCommand, Function.identity()));
    }

    public BotCommand getBotCommand(Command command) {
        return botCommands.get(command);
    }

    public BotCommand getBotCommand(State state) {
        return botCommandsByState.get(state);
    }

    public BotCommand getBotCommand(String cmd) {
        return botCommandsByString.get(cmd);
    }
}
