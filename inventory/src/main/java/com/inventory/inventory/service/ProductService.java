package com.inventory.inventory.service;

import com.inventory.inventory.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class ProductService {

    private Random random = new Random();

    public ProductDTO findAvailableProduct(String uniqId) {
        return new ProductDTO(uniqId, random.nextInt(10));
    }

}
