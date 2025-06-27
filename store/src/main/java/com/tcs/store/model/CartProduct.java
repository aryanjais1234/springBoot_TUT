package com.tcs.store.model;

import jakarta.persistence.*;

@Table(name = "CartProduct")
@Entity
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartProductId;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "cartId")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private Product product;

    private int quantity;

    public CartProduct(int cartProductId, Cart cart, Product product, int quantity) {
        this.cartProductId = cartProductId;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public CartProduct() {
    }

    public int getCartProductId() {
        return cartProductId;
    }

    public void setCartProductId(int cartProductId) {
        this.cartProductId = cartProductId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
