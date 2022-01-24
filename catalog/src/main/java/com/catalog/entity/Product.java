package com.catalog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Products")
@AllArgsConstructor
public class Product {
    @Id
    @Column(name = "uniq_id")
    private String uniqId;
    private String sku;
    @Column(name = "name_title")
    private String nameTitle;
    @Column(name = "list_price")
    private String listPrice;
    @Column(name = "sale_price")
    private String salePrice;
    private String category;
    @Column(name = "category_tree")
    private String categoryTree;
    @Column(name = "average_product_rating")
    private String averageProductRating;

    public Product() {
        //do nothing
    }
}
