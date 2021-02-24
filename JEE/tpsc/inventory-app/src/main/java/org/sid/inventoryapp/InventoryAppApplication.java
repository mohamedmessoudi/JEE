package org.sid.inventoryapp;

import org.sid.inventoryapp.entities.Product;
import org.sid.inventoryapp.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryAppApplication.class, args);
    }


    @Bean
    CommandLineRunner start(ProductRepository productRepository){
        return args -> {
            productRepository.save(new Product(null,"Computer",7600,34));
            productRepository.save(new Product(null,"Printer",1600,134));
            productRepository.save(new Product(null,"Smartphone",1600,34));
        };
    }
}
