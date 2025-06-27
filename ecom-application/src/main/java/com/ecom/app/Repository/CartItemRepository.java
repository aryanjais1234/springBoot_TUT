package com.ecom.app.Repository;

import com.ecom.app.Models.CartItem;
import com.ecom.app.Models.Product;
import com.ecom.app.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);
}
