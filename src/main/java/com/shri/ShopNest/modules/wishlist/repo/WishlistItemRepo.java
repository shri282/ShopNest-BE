package com.shri.ShopNest.modules.wishlist.repo;

import com.shri.ShopNest.modules.wishlist.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistItemRepo extends JpaRepository<WishlistItem, Long> {
}
