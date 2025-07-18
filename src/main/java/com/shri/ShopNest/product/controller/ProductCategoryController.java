package com.shri.ShopNest.product.controller;

import com.shri.ShopNest.product.dto.ProductCategoryDto;
import com.shri.ShopNest.product.service.ProductCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products/category")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ProductCategoryDto>> findAll() {
        return ResponseEntity.ok(productCategoryService.findAll());
    }

}
