package com.shri.ShopNest.modules.product.repo;

import com.shri.ShopNest.modules.product.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Long> {}
