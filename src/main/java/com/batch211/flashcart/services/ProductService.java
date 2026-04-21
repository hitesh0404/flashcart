package com.batch211.flashcart.services;

import java.util.List;
import com.batch211.flashcart.entities.Product;

import com.batch211.flashcart.dto.ProductRequest;


public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Integer id);
    Product createProduct(ProductRequest request);
    Product updateProduct(Integer id, ProductRequest request);
    void deleteProduct(Integer id);
}