package com.shri.ShopNest.user.service;

import com.shri.ShopNest.exception.exceptions.ResourceNotFoundException;
import com.shri.ShopNest.user.model.User;
import com.shri.ShopNest.user.model.UserAddress;
import com.shri.ShopNest.user.repo.UserAddressRepo;
import com.shri.ShopNest.user.repo.UserRepo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final UserAddressRepo userAddressRepo;

    public UserService(UserRepo userRepo, UserAddressRepo userAddressRepo) {
        this.userRepo = userRepo;
        this.userAddressRepo = userAddressRepo;
    }

    private User getUserOrThrow(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findOne(Long id) {
        return getUserOrThrow(id);
    }

    public User create(User user) {
        return userRepo.save(user);
    }

    public User update(User user) {
        return userRepo.save(user);
    }

    public void remove(Long id) {
        getUserOrThrow(id);
        userRepo.deleteById(id);
    }

    public UserAddress addAddress(Long userId, UserAddress newAddress) {
        User user = getUserOrThrow(userId);

        if (Boolean.TRUE.equals(newAddress.getIsDefault())) {
            userAddressRepo.findByUserAndIsDefaultTrue(user)
                    .ifPresent(existingDefault -> {
                        existingDefault.setIsDefault(false);
                        userAddressRepo.save(existingDefault);
                    });
        }

        newAddress.setUser(user);
        return userAddressRepo.save(newAddress);
    }

    public UserAddress updateAddress(Long userId, UserAddress address) {
        User user = getUserOrThrow(userId);

        // In case
        UserAddress existingAddress = userAddressRepo.findById(address.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + address.getId()));

        if (!existingAddress.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("This address does not belong to the user.");
        }

        if (Boolean.TRUE.equals(address.getIsDefault())) {
            userAddressRepo.findByUserAndIsDefaultTrue(user)
                    .ifPresent(existingDefault -> {
                        existingDefault.setIsDefault(false);
                        userAddressRepo.save(existingDefault);
                    });
        }

        address.setUser(user);
        return userAddressRepo.save(address);
    }

    public List<UserAddress> getAddresses(Long userId) {
        User user = getUserOrThrow(userId);
        return userAddressRepo.findByUser(user);
    }

    public Optional<UserAddress> getDefaultAddress(Long userId) {
        User user = getUserOrThrow(userId);
        return userAddressRepo.findByUserAndIsDefaultTrue(user);
    }

}