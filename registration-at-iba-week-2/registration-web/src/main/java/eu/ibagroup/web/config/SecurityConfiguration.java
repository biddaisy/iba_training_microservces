package eu.ibagroup.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain confirmationSecurity(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .requestMatchers( requests-> requests
                        .antMatchers("/confirmation/**")
                );
        return http.build();
    }

    @Bean
    public SecurityFilterChain defaultSecurity(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .requiresChannel()
                .anyRequest().requiresSecure();
        return http.build();
    }
}


