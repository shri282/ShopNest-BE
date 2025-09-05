package com.shri.ShopNest.modules.wishlist.service;

import com.shri.ShopNest.exception.exceptions.ResourceNotFoundException;
import com.shri.ShopNest.modules.product.model.Product;
import com.shri.ShopNest.modules.product.service.ProductService;
import com.shri.ShopNest.modules.user.model.User;
import com.shri.ShopNest.modules.user.service.UserService;
import com.shri.ShopNest.modules.wishlist.dto.AddWishlistItemRequest;
import com.shri.ShopNest.modules.wishlist.dto.WishlistDto;
import com.shri.ShopNest.modules.wishlist.mapper.WishlistMapper;
import com.shri.ShopNest.modules.wishlist.model.Wishlist;
import com.shri.ShopNest.modules.wishlist.model.WishlistItem;
import com.shri.ShopNest.modules.wishlist.repo.WishlistRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepo wishlistRepo;
    private final UserService userService;
    private final ProductService productService;

    public WishlistService(WishlistRepo wishlistRepo,
                           UserService userService,
                           ProductService productService) {
        this.wishlistRepo = wishlistRepo;
        this.userService = userService;
        this.productService = productService;
    }

    public WishlistDto getDefaultWishlist(Long userId) {
        User user = userService.findOne(userId);
        List<Wishlist> wishlists = wishlistRepo.findByUser(user);
        Wishlist wishlist = wishlists.stream()
                .filter(Wishlist::isDefault)
                .findFirst()
                .orElse(null);

        if (wishlist == null) {
            throw new ResourceNotFoundException("no default wishlist found");
        }

        return WishlistMapper.toDto(wishlist);
    }

    public List<WishlistDto> getAllWishlist(Long userId) {
        User user = userService.findOne(userId);
        List<Wishlist> wishlists = wishlistRepo.findByUser(user);
        return wishlists.stream().map(WishlistMapper::toDto).toList();
    }

    public WishlistDto addItem(AddWishlistItemRequest request, Long userId) {
        User user = userService.findOne(userId);

        Wishlist wishlist;
        if (request.getWishlistId() != null) {
            wishlist = wishlistRepo.findById(request.getWishlistId())
                    .orElseThrow(() -> new RuntimeException("Wishlist not found for user"));
        } else {
            wishlist = wishlistRepo.findDefaultByUser(user)
                    .orElseGet(() -> {
                        Wishlist newWishlist = Wishlist.builder()
                                .name("Default Wishlist")
                                .isDefault(true)
                                .user(user)
                                .build();
                        return wishlistRepo.save(newWishlist);
                    });
        }

        Product product = productService.findOne(request.getProductId());

        WishlistItem wishlistItem = WishlistItem.builder()
                .notes(request.getNotes())
                .priority(3)
                .wishlist(wishlist)
                .product(product)
                .build();

        wishlist.getItems().add(wishlistItem);

        return WishlistMapper.toDto(wishlistRepo.save(wishlist));
    }

}
