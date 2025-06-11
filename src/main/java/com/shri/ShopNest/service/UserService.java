package com.shri.ShopNest.service;

import com.shri.ShopNest.exception.exceptions.ResourceNotFoundException;
import com.shri.ShopNest.model.User;
import com.shri.ShopNest.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    public User findOne(int id) throws Exception {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User create(User user) {
        return userRepo.save(user);
    }

    public User update(User user) {
        return userRepo.save(user);
    }

    public void remove(int id) throws Exception {
        userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        userRepo.deleteById(id);
    }
}
