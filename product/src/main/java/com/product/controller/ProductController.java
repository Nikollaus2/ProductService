package com.product.controller;

import com.product.entity.ProductDTO;
import com.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{code}/id")
    public ProductDTO productByUniqId(@PathVariable("code") String uniqId) {
        logger.info("productByUniqId was called with {}.", uniqId);
        return productService.findProductByUniqId(uniqId);
    }

    @GetMapping("/{code}/sku")
    public List<ProductDTO> productBySku(@PathVariable("code") String sku) {
        logger.info("productBySku was called with {}.", sku);
        return productService.findProductBySku(sku);
    }
}
