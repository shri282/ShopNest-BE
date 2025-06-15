package com.shri.ShopNest.user.controller;

import com.shri.ShopNest.user.model.User;
import com.shri.ShopNest.user.service.UserService;
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
    public User findOne(@PathVariable int id) throws Exception {
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
    public String remove(@PathVariable int id) throws Exception {
        userService.remove(id);
        return "user removed successfully";
    }
}
