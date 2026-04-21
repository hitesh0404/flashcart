package com.batch211.flashcart.serviceimpl;

import com.batch211.flashcart.entities.Brand;
import com.batch211.flashcart.repo.BrandRepository;
import com.batch211.flashcart.services.BrandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id " + id));
    }

    @Override
    public Brand create(Brand brand) {
        if (brandRepository.existsByNameIgnoreCase(brand.getName())) {
            throw new RuntimeException("Brand already exists");
        }
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(Long id, Brand brand) {
        Brand existing = getById(id);
        existing.setName(brand.getName());
        return brandRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        brandRepository.deleteById(id);
    }
}