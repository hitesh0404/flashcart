package com.batch211.flashcart.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.batch211.flashcart.entities.Brand;

import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByNameIgnoreCase(String name);
}