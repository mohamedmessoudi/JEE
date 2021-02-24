package org.sid.inventoryapp.web;

import org.sid.inventoryapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ProductController {
    @Autowired private ProductRepository productRepository;
    @GetMapping("/prods")
    public String prods(Model model){
        model.addAttribute("products",productRepository.findAll());
        return "Products";
    }
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
