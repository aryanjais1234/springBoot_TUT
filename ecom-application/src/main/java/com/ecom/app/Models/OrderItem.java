package com.ecom.app.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    /*
    -   @ManyToOne with Product:
        Many OrderItems can refer to the same Product.
        For example, if 100 people order the same phone,
        you’ll have 100 OrderItem entries all pointing to that one Product.
    -   @ManyToOne with Order:
        A single Order can contain multiple OrderItems—each
        representing a different product (or the same product with
        different quantities).
        So many OrderItems belong to one Order

     */
}
