package com.shri.ShopNest.repo;

import com.shri.ShopNest.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepo extends JpaRepository<ProductReview, Long> {
    public List<ProductReview> findByProductId(int productId);
}
