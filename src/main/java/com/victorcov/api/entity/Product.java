package com.victorcov.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Document(collection = "products")
public class Product {
    private String productId;
    private String name;
    private String description;
    private double price;
}
