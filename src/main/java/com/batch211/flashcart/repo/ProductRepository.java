package com.batch211.flashcart.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.batch211.flashcart.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // later we can add:
    // List<Product> findByBrand_Id(Long brandId);
    // List<Product> findByCategories_Id(Integer categoryId);
}