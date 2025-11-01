package com.shri.ShopNest.controller;

import com.shri.ShopNest.modules.cart.dto.CartDto;
import com.shri.ShopNest.modules.cart.dto.CartItemCountResponse;
import com.shri.ShopNest.exception.exceptions.ResourceNotFoundException;
import com.shri.ShopNest.modules.cart.mapper.CartMapper;
import com.shri.ShopNest.model.Cart;
import com.shri.ShopNest.modules.cart.service.CartService;
import com.shri.ShopNest.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/users/{userId}/cart")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartDto> getUserActiveCart(@PathVariable("userId") long userId) {
        Optional<Cart> cart = cartService.findUserActiveCart(userId);
        return cart
                .map(cartEntity -> ResponseEntity.ok(CartMapper.toCartDTO(cartEntity)))
                .orElseThrow(() -> new ResourceNotFoundException("No active cart found for user "+userId));
    }

    @PostMapping
    public ResponseEntity<CartDto> addItemToCart(@PathVariable("userId") long userId, @RequestBody Product product) {
        return new ResponseEntity<>(CartMapper.toCartDTO(cartService.addToCart(userId, product)), HttpStatus.CREATED);
    }

    @GetMapping("/count")
    public ResponseEntity<CartItemCountResponse> getUserCartTotal(@PathVariable("userId") long userId) {
        Optional<Cart> optionalCart = cartService.findUserActiveCart(userId);

        Integer totalItems = optionalCart
                .map(cart -> cart.getItems().size())
                .orElse(null);

        return ResponseEntity.ok(new CartItemCountResponse(totalItems));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        cartService.delete(id);
        return new ResponseEntity<>("cart deleted successfully", HttpStatus.OK);
    }

    @PutMapping("cartItem/{itemId}/addQuantity")
    public ResponseEntity<CartDto> addCartItemQuantity(@PathVariable("itemId") long itemId,
                                                       @RequestParam("quantity") int quantity) {
        CartDto cartDto = cartService.addCartItemQuantity(itemId, quantity);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @PutMapping("cartItem/{itemId}/remove")
    public ResponseEntity<CartDto> removeCartItem(@PathVariable("itemId") long itemId) {
        CartDto cartDto = cartService.removeCartItem(itemId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

}
