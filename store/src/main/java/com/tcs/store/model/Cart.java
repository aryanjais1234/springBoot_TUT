package com.tcs.store.model;

import com.tcs.store.model.Users;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "Cart")
@Entity
public class Cart {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    private double totalAmount;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartProduct> cartProductList;

    public Cart() {
    }

    public Cart(int cartId, double totalAmount, Users user, List<CartProduct> cartProductList) {
        this.cartId = cartId;
        this.totalAmount = totalAmount;
        this.user = user;
        this.cartProductList = cartProductList;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<CartProduct> getCartProductList() {
        return cartProductList;
    }

    public void setCartProductList(List<CartProduct> cartProductList) {
        this.cartProductList = cartProductList;
    }
}
