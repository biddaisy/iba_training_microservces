package eu.ibagroup.bot.config;

import eu.ibagroup.bot.command.Command;
import eu.ibagroup.bot.telegram.command.BotCommand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    private final MessageConfiguration messageConfiguration;

    private final ListableBeanFactory beanFactory;

    /**
     * Contains TG bot name picked during bot registration
     */
    private String name;

    /**
     * Contains TG bot token assigned during bot registration
     */
    private String accessToken;

    private Map<Command, BotCommand> botCommands;

    private Map<String, BotCommand> botCommandsByQualifiedName;

    @PostConstruct
    public void init() {
        initBotCommands();
        initBotCommandsByQualifiedName();
        initHelpMessage();
    }

    private void initHelpMessage() {
        messageConfiguration.setHelpMessage(getHelpMessage());
    }

    private void initBotCommandsByQualifiedName() {
        botCommandsByQualifiedName = beanFactory.getBeansOfType(BotCommand.class).values().stream()
                .collect(Collectors.toUnmodifiableMap(BotConfiguration::getCommandNameQualified, Function.identity()));
    }

    private void initBotCommands() {
        botCommands = beanFactory.getBeansOfType(BotCommand.class).values().stream()
                .collect(Collectors.toUnmodifiableMap(BotCommand::getCommand, Function.identity()));
    }

    public BotCommand getBotCommand(Command command) {
        return botCommands.get(command);
    }

    public BotCommand getBotCommand(String command) {
        return botCommandsByQualifiedName.get(command);
    }

    private String getHelpMessage() {
        return Stream.of(Command.values())
                .map(this::getBotCommand)
                .map(BotConfiguration::getCommandDescription)
                .collect(Collectors.joining("\n"));
    }

    private static String getCommandDescription(BotCommand botCommand) {
        return getCommandNameQualified(botCommand) + " -- " + botCommand.getDescription();
    }

    private static String getCommandNameQualified(BotCommand botCommand) {
        return "/" + botCommand.getCommand().name().toLowerCase();
    }

}
