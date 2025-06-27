package com.ecom.app.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
    private Boolean active;
}
