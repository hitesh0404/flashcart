package com.batch211.flashcart.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.batch211.flashcart.entities.Brand;
import com.batch211.flashcart.entities.Product;

public interface BrandRepo extends JpaRepository<Brand, Long>{
	List<Product> findByBrandId(Long brandId);
	List<Product> findByBrand(Brand brand);
}
