package com.shri.ShopNest.modules.product.repo;

import com.shri.ShopNest.modules.product.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepo extends JpaRepository<ProductReview, Long> {
    public List<ProductReview> findByProductId(int productId);
}
