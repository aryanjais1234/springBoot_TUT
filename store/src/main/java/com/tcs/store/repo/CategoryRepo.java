package com.tcs.store.repo;

import com.tcs.store.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryRepo,Integer> {
    Category findByCategoryName(String name);
}
