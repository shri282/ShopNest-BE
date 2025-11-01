package com.shri.ShopNest.controller;

import com.shri.ShopNest.modules.product.dto.ProductCategoryResponse;
import com.shri.ShopNest.modules.product.service.ProductCategoryService;
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
    public ResponseEntity<List<ProductCategoryResponse>> findAll() {
        return ResponseEntity.ok(productCategoryService.findAll());
    }

}
