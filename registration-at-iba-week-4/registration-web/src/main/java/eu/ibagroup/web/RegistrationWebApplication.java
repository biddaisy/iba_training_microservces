package eu.ibagroup.web;

import lombok.val;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@ComponentScan({"eu.ibagroup.common", "eu.ibagroup.web"}) // scan components in both modules
@EnableMongoRepositories("eu.ibagroup.common") // enable Mongo repositories located in common module
@EnableAsync
@PropertySource("application-common.properties")
@PropertySource("application.properties")
public class RegistrationWebApplication {

    @Value("${http.port}")
    private int httpPort;

    public static void main(String[] args) {
        SpringApplication.run(RegistrationWebApplication.class, args);
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        val tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
        return tomcat;
    }

    private Connector createStandardConnector() {
        val connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(httpPort);
        return connector;
    }
}
