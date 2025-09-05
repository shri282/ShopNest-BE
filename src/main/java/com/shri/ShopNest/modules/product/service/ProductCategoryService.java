package com.shri.ShopNest.modules.product.service;

import com.shri.ShopNest.exception.exceptions.ResourceNotFoundException;
import com.shri.ShopNest.modules.product.dto.ProductCategoryResponse;
import com.shri.ShopNest.modules.product.mapper.ProductCategoryMapper;
import com.shri.ShopNest.modules.product.model.ProductCategory;
import com.shri.ShopNest.modules.product.repo.ProductCategoryRepo;
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
