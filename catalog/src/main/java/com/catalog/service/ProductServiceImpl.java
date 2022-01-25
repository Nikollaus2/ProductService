package com.catalog.service;

import com.catalog.entity.Product;
import com.catalog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findProductByUniqId(String uniqId) {
        return productRepository.findProductByUniqId(uniqId);
    }

    @Override
    public List<Product> findProductBySku(String sku) {
        return productRepository.findProductBySku(sku);
    }
}
