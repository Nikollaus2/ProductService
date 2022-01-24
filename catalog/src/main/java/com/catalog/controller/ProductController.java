package com.catalog.controller;

import com.catalog.entity.Product;
import com.catalog.exception.ProductNotFoundException;
import com.catalog.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productsCatalog")
public class ProductController {

    private ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public List<Product> allProducts() {
        logger.info("allProducts was called");
        return productService.findAllProducts();
    }

    @GetMapping("/{code}/id")
    public Product productByUniqId(@PathVariable("code") String uniqId) {
        logger.info("productByUniqId was called with {}", uniqId);
        return productService.findProductByUniqId(uniqId)
                .orElseThrow(() -> new ProductNotFoundException("Product with uniqId ["+uniqId+"] doesn't exist"));
    }

    @GetMapping("/{code}/sku")
    public List<Product> productBySku(@PathVariable("code") String sku) {
        logger.info("productBySku was called with {}", sku);
        List<Product> productList = productService.findProductBySku(sku);
        if (productList == null || productList.isEmpty()){
            throw new ProductNotFoundException("Product with sku ["+sku+"] doesn't exist");
        }
        return productList;
    }
}
