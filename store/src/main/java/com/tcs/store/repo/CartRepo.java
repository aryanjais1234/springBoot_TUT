package com.tcs.store.repo;

import com.tcs.store.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart,Integer> {
    Cart findByUserIdUserName(String name);
}
