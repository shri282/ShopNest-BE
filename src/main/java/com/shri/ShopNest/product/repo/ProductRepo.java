package com.shri.ShopNest.product.repo;

import com.shri.ShopNest.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByBrandContainingIgnoreCase(String brand);

    List<Product> findByCategoryNameContainingIgnoreCase(String category);

    List<Product> findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(String name, String brand, String category);

}
