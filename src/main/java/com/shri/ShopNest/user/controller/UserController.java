package com.shri.ShopNest.user.controller;

import com.shri.ShopNest.user.model.User;
import com.shri.ShopNest.user.model.UserAddress;
import com.shri.ShopNest.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public User findOne(@PathVariable Long id) throws Exception {
        return userService.findOne(id);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("{id}")
    public String remove(@PathVariable Long id) throws Exception {
        userService.remove(id);
        return "user removed successfully";
    }

    @PostMapping("/{id}/add-address")
    public ResponseEntity<UserAddress> addUserAddress(@PathVariable Long id, @RequestBody UserAddress address) {
        UserAddress userAddress = userService.addAddress(id, address);
        return ResponseEntity.ok(userAddress);
    }

    @PutMapping("/{id}/update-address")
    public ResponseEntity<UserAddress> updateUserAddress(@PathVariable Long id, @RequestBody UserAddress address) {
        UserAddress userAddress = userService.updateAddress(id, address);
        return ResponseEntity.ok(userAddress);
    }

    @GetMapping("/{id}/address")
    public ResponseEntity<List<UserAddress>> getUserAddresses(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getAddresses(id));
    }

    @GetMapping("/{id}/address/default")
    public ResponseEntity<Optional<UserAddress>> getUserAddress(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getDefaultAddress(id));
    }

}
