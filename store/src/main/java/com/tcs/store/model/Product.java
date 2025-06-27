package com.tcs.store.model;

import com.tcs.store.model.Users;
import jakarta.persistence.*;

@Table(name = "Product")
@Entity
public class Product {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String productName;

    private double price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_Id",referencedColumnName = "userId")
    private Users seller;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_Id", referencedColumnName = "categoryId")
    private Category category;

    public Product(int productId, String productName, double price, Users seller, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.seller = seller;
        this.category = category;
    }

    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Users getSeller() {
        return seller;
    }

    public void setSeller(Users seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
