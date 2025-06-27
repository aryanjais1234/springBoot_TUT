package com.tcs.store.repo;


import com.tcs.store.model.CartProduct;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductRepo extends JpaRepository<CartProduct,Integer> {
    @Transactional
    void deleteByCartUserIdUseridAndProductProductId(Integer userid, Integer productId);

    Optional<CartProduct> findByCartUserIdUseridAndProductProductId(Integer userid, Integer productId);

    List<CartProduct> findByProductProductId(Integer productId);
}
