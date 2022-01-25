package com.product.service;

import com.product.entity.ProductDTO;

import java.util.List;

public interface ProductService {
    public ProductDTO findProductByUniqId(String uniqId);
    public List<ProductDTO> findProductBySku(String sku);
}
