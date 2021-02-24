package org.sid.suppliersservice.web;

import lombok.Data;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.sid.suppliersservice.entities.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

public class SupplierController {
    @Autowired
    private KeycloakRestTemplate keycloakRestTemplate;

    @GetMapping("/suppliers")
    @ResponseBody
    public Collection<Supplier> suppliers() {
        ResponseEntity<PagedModel<Supplier>> response =

                keycloakRestTemplate.exchange("http://localhost:8083/suppliers",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<PagedModel<Supplier>>() {
                        });

        return response.getBody().getContent();
    }

    @GetMapping("/listSuppliers")
    public String listSuppliers(Model model) {
        ResponseEntity<PagedModel<Supplier>> response =

                keycloakRestTemplate.exchange("http://localhost:8083/suppliers",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<PagedModel<Supplier>>() {
                        });

        model.addAttribute("suppliers", response.getBody());

        return "listSuppliers";
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Model model) {
        model.addAttribute("errorMessage", "Not Authorized");
        return "errors";
    }


    @Bean
    KeycloakRestTemplate keycloakRestTemplate(KeycloakClientRequestFactory
                                                      keycloakClientRequestFactory) {

        return new KeycloakRestTemplate(keycloakClientRequestFactory);

    }

    @Data
    class Supplier{
        private Long id;
        private String name;
        private String email;
    }
}
