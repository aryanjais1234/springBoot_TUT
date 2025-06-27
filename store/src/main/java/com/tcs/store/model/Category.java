package com.tcs.store.model;

import jakarta.persistence.*;

import java.util.List;

@Table(name="Category")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    private String categroyName;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Product> productList;

    public Category(int categoryId, String categroyName, List<Product> productList) {
        this.categoryId = categoryId;
        this.categroyName = categroyName;
        this.productList = productList;
    }

    public Category() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategroyName() {
        return categroyName;
    }

    public void setCategroyName(String categroyName) {
        this.categroyName = categroyName;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
