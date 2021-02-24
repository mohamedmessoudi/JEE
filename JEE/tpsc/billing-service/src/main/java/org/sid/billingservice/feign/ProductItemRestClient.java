package org.sid.billingservice.feign;

import org.sid.billingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="PRODUCT-SERVICE")
public interface ProductItemRestClient {

        @GetMapping("/products/{id}?projection=fullProduct")
        Product findProductById(@PathVariable("id") Long id);
        @GetMapping("/products?projection=fullProduct")
        PagedModel<Product> findAll();

    public static PagedModel<Product> findAllProducts() {
        return null;
    }
}

