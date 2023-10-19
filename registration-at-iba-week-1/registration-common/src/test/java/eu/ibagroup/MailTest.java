package eu.ibagroup;

import eu.ibagroup.service.SendMailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@Slf4j
@PropertySources({
        @PropertySource("classpath:application-common.properties")
})
public class MailTest implements CommandLineRunner {

    @Value("${registration.web.url}")
    String url;

    @Autowired
    SendMailService mailService;

    public static void main(String[] args) {
        new SpringApplicationBuilder(MailTest.class)
                .web(WebApplicationType.NONE) // disable default Tomcat server, we don't need to listen HTTP(S)  port
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("URL ===> " + url);
        mailService.sendNotification("test-abc-def", "mramanovich@ibagroup.eu");
    }
}

