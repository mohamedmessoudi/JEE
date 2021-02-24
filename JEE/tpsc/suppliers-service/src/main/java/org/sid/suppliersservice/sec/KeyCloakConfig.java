package org.sid.suppliersservice.sec;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyCloakConfig {
    @Bean
    KeycloakSpringBootConfigResolver keycloakConfigResolver(){return new KeycloakSpringBootConfigResolver(); }
}
