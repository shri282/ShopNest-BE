package com.shri.ShopNest.controller;

import com.shri.ShopNest.modules.wishlist.dto.AddWishlistItemRequest;
import com.shri.ShopNest.modules.wishlist.dto.WishlistDto;
import com.shri.ShopNest.modules.wishlist.dto.WishlistSummaryDto;
import com.shri.ShopNest.modules.wishlist.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}/wishlists")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("default")
    public ResponseEntity<WishlistDto> getDefaultWishlist(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(wishlistService.getDefaultWishlist(userId));
    }

    @GetMapping
    public ResponseEntity<List<WishlistDto>> getAllWishlist(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(wishlistService.getAllWishlist(userId));
    }

    @GetMapping("{wishlistId}")
    public ResponseEntity<WishlistDto> getWishlist(@PathVariable("wishlistId") Long wishlistId) {
        return ResponseEntity.ok(wishlistService.getWishlist(wishlistId));
    }

    @PostMapping("addItem")
    public ResponseEntity<WishlistDto> addItem(@RequestBody AddWishlistItemRequest request, @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(wishlistService.addItem(request, userId));
    }

    @GetMapping("summary")
    public ResponseEntity<List<WishlistSummaryDto>> getAllWishlistSummary(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(wishlistService.getAllWishlistSummary(userId));
    }

    @PostMapping("/{wlId}/move-to-cart")
    public ResponseEntity<String> moveWlToCart(@PathVariable("userId") Long userId, @PathVariable("wlId") Long wlId) {
        return ResponseEntity.ok(wishlistService.moveWlToCart(wlId, userId));
    }

    @PostMapping("/{wlId}/items/{itemId}/move-to-cart")
    public ResponseEntity<String> moveWlItemToCart(@PathVariable("userId") Long userId, @PathVariable("itemId") Long itemId, @PathVariable("wlId") Long wlId) {
        return ResponseEntity.ok(wishlistService.moveWlItemToCart(wlId, itemId, userId));
    }

}
