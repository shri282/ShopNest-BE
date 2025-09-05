package com.shri.ShopNest.modules.wishlist.repo;

import com.shri.ShopNest.modules.user.model.User;
import com.shri.ShopNest.modules.wishlist.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepo extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser(User user);
    Optional<Wishlist> findDefaultByUser(User user);
}
