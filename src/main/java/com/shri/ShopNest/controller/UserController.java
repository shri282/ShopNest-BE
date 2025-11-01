package com.shri.ShopNest.controller;

import com.shri.ShopNest.model.User;
import com.shri.ShopNest.model.UserAddress;
import com.shri.ShopNest.modules.user.service.UserService;
import com.shri.ShopNest.modules.user.dto.AddressResponse;
import com.shri.ShopNest.modules.user.dto.CreateAddressReq;
import com.shri.ShopNest.modules.user.dto.UpdateAddressReq;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<UserAddress> addUserAddress(@PathVariable Long id, @RequestBody CreateAddressReq req) {
        UserAddress userAddress = userService.addAddress(id, req);
        return ResponseEntity.ok(userAddress);
    }

    @PutMapping("/{id}/update-address")
    public ResponseEntity<UserAddress> updateUserAddress(@PathVariable Long id,
                                                         @Valid @RequestBody UpdateAddressReq req) {
        UserAddress userAddress = userService.updateAddress(id, req);
        return ResponseEntity.ok(userAddress);
    }

    @GetMapping("/{id}/address")
    public ResponseEntity<List<AddressResponse>> getUserAddresses(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getAddresses(id));
    }

    @GetMapping("/{id}/address/default")
    public ResponseEntity<AddressResponse> getUserAddress(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getDefaultAddress(id));
    }

}
