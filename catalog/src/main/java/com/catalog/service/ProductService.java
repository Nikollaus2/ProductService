package com.catalog.service;

import com.catalog.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<Product> findAllProducts();
    public Optional<Product> findProductByUniqId(String uniqId);
    public List<Product> findProductBySku(String sku);
}
