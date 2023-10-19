package eu.ibagroup.bot.config;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@Getter
@Setter
public class MessageConfiguration {

    private String helpMessage;

    @Bean
    public MessageSource messageSource() {
        val messageSource = new ResourceBundleMessageSource ();
        messageSource.setBasename("messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}


