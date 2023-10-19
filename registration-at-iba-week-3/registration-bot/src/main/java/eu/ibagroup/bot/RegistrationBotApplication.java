package eu.ibagroup.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Locale;

@Slf4j
@SpringBootApplication
@ComponentScan({"eu.ibagroup.common", "eu.ibagroup.bot"}) // scan components in both modules
@EnableMongoRepositories("eu.ibagroup.common") // enable Mongo repositories located in common module
@EnableAsync
@PropertySource("classpath:application-common.properties")
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class RegistrationBotApplication implements CommandLineRunner {

    private final MessageSource messageSource;

    @Value("${bot.default.language}")
    private String lang;

    public static void main(String[] args) {
        new SpringApplicationBuilder(RegistrationBotApplication.class)
                .web(WebApplicationType.NONE) // disable default Tomcat server, we don't need to listen HTTP(S)  port
                .run(args);
    }

    /**
     * Perform bot configuration on startup
     */
    @Override
    public void run(String... args) throws Exception {
        Locale.setDefault(new Locale.Builder().setLanguage(lang).build());
        log.info(messageSource.getMessage("bot.started", null, Locale.getDefault()));
    }
}
