package com.product.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductAvailabilityDTO {
    private String uniqId;
    private Integer numberOfAvailableProduct;

    public String getUniqId() {
        return uniqId;
    }

    public Integer getNumberOfAvailableProduct() {
        return numberOfAvailableProduct;
    }

    public ProductAvailabilityDTO() {
        //do nothing
    }
}
