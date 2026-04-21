package com.batch211.flashcart.serviceimpl;

import com.batch211.flashcart.dto.ProductRequest;
import com.batch211.flashcart.entities.Brand;
import com.batch211.flashcart.entities.Category;
import com.batch211.flashcart.entities.Product;
import com.batch211.flashcart.repo.BrandRepository;
import com.batch211.flashcart.repo.CategoryRepository;
import com.batch211.flashcart.repo.ProductRepository;
import com.batch211.flashcart.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              BrandRepository brandRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public Product createProduct(ProductRequest request) {
        Product product = new Product();
        mapRequestToProduct(request, product);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Integer id, ProductRequest request) {
        Product existing = getProductById(id);
        mapRequestToProduct(request, existing);
        return productRepository.save(existing);
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    private void mapRequestToProduct(ProductRequest request, Product product) {
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        // Brand
        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found with id " + request.getBrandId()));
            product.setBrand(brand);
        } else {
            product.setBrand(null);
        }

        // Categories
        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            List<Category> categories = new ArrayList<>();
            for (Integer catId : request.getCategoryIds()) {
                Category category = categoryRepository.findById(catId)
                        .orElseThrow(() -> new RuntimeException("Category not found with id " + catId));
                categories.add(category);
            }
            product.setCategories(categories);
        } else {
            product.setCategories(new ArrayList<>());
        }
    }
}