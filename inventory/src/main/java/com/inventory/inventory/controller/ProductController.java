package com.inventory.inventory.controller;

import com.inventory.inventory.dto.ProductDTO;
import com.inventory.inventory.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/availableProducts")
@Slf4j
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ProductDTO productByUniqId(@PathVariable String id) {
        return productService.findAvailableProduct(id);
    }

}
