package com.ecom.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long id;
    private String userName;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
