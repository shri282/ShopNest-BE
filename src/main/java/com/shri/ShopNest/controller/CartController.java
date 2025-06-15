package com.shri.ShopNest.controller;

import com.shri.ShopNest.dto.cart.CartDto;
import com.shri.ShopNest.exception.exceptions.ResourceNotFoundException;
import com.shri.ShopNest.mapper.CartMapper;
import com.shri.ShopNest.model.Cart;
import com.shri.ShopNest.model.CartItem;
import com.shri.ShopNest.model.Product;
import com.shri.ShopNest.service.CartItemService;
import com.shri.ShopNest.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users/{userId}/cart")
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;

    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @GetMapping
    public ResponseEntity<CartDto> getUserActiveCart(@PathVariable("userId") long userId) {
        Optional<Cart> cart = cartService.findUserActiveCart(userId);
        return cart
                .map(cartEntity -> ResponseEntity.ok(cartMapper.toCartDTO(cartEntity)))
                .orElseThrow(() -> new ResourceNotFoundException("No active cart found for user "+userId));
    }

    @PostMapping
    public ResponseEntity<CartDto> addItemToCart(@PathVariable("userId") long userId, @RequestBody Product product) {
        return new ResponseEntity<>(cartMapper.toCartDTO(cartService.addToCart(userId, product)), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable long id) throws Exception {
        cartService.delete(id);
        return new ResponseEntity<>("cart deleted successfully", HttpStatus.OK);
    }

    @PutMapping("cartItem/{itemId}/addQuantity")
    public ResponseEntity<CartDto> addCartItemQuantity(@PathVariable("itemId") long itemId,
                                                       @RequestParam("quantity") int quantity) throws Exception {
        CartDto cartDto = cartService.addCartItemQuantity(itemId, quantity);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @PutMapping("cartItem/{itemId}/remove")
    public ResponseEntity<CartDto> removeCartItem(@PathVariable("itemId") long itemId) throws Exception {
        CartDto cartDto = cartService.removeCartItem(itemId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

}
