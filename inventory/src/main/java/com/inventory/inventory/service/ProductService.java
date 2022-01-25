package com.inventory.inventory.service;

import com.inventory.inventory.dto.ProductDTO;

public interface ProductService {
    public ProductDTO findAvailableProduct(String uniqId);
}
