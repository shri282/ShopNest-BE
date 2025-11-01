package com.shri.ShopNest.repo;

import com.shri.ShopNest.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistItemRepo extends JpaRepository<WishlistItem, Long> {
}
