package org.sid.billingservice;

import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.entities.ProductItem;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.ProductItemRestClient;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(
            BillRepository billRepository,
            ProductItemRepository productItemRepository,
            CustomerRestClient customerService,
            ProductItemRestClient inventoryService) {
        return args -> {
            Customer c1=customerService.findCustomerById(1L);
            Product p1=inventoryService.findProductById(1L);
            System.out.println("===============================================================");
            System.err.println(c1);
            System.out.println("===============================================================");
            System.err.println(p1);
            System.out.println("===============================================================");
            Bill bill1 = billRepository.save(
                    new Bill(null, new Date(), c1.getId(),null, null));

            PagedModel<Product> products=ProductItemRestClient.findAllProducts();
            products.getContent().forEach(
                    p->{
                        productItemRepository.save(
                                new ProductItem(null, p.getId(),null,
                                        p.getPrice(), 20, bill1));
                    }
            );


        };
    }

}

