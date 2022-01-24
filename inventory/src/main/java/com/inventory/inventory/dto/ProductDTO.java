package com.inventory.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
    private String uniqId;
    private Integer numberOfAvailableProduct;
}
