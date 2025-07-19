package com.shri.ShopNest.product.service;

import com.shri.ShopNest.exception.exceptions.ResourceNotFoundException;
import com.shri.ShopNest.product.dto.ProductCategoryResponse;
import com.shri.ShopNest.product.mapper.ProductCategoryMapper;
import com.shri.ShopNest.product.model.ProductCategory;
import com.shri.ShopNest.product.repo.ProductCategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepo productCategoryRepo;

    public ProductCategoryService(ProductCategoryRepo productCategoryRepo) {
        this.productCategoryRepo = productCategoryRepo;
    }
    public List<ProductCategoryResponse> findAll() {
        return productCategoryRepo.findAll().stream().map(ProductCategoryMapper::toDto).toList();
    }

    public ProductCategory findOne(Long id) {
        return productCategoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("product category not found"));
    }
}
