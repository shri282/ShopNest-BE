package com.shri.ShopNest.modules.user.service;

import com.shri.ShopNest.exception.exceptions.ResourceNotFoundException;
import com.shri.ShopNest.modules.user.model.User;
import com.shri.ShopNest.modules.user.repo.UserRepo;
import com.shri.ShopNest.modules.user.dto.AddressResponse;
import com.shri.ShopNest.modules.user.dto.CreateAddressReq;
import com.shri.ShopNest.modules.user.dto.UpdateAddressReq;
import com.shri.ShopNest.modules.user.mapper.AddressMapper;
import com.shri.ShopNest.modules.user.model.UserAddress;
import com.shri.ShopNest.modules.user.repo.UserAddressRepo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public UserAddress addAddress(Long userId, CreateAddressReq req) {
        UserAddress address = AddressMapper.toEntity(req);
        User user = getUserOrThrow(userId);

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

    public UserAddress updateAddress(Long userId, UpdateAddressReq req) {
        UserAddress address = AddressMapper.toEntity(req);
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

    public List<AddressResponse> getAddresses(Long userId) {
        User user = getUserOrThrow(userId);
        return userAddressRepo.findByUser(user).stream().map(AddressMapper::toDto).toList();
    }

    public AddressResponse getDefaultAddress(Long userId) {
        User user = getUserOrThrow(userId);
        UserAddress address = userAddressRepo.findByUserAndIsDefaultTrue(user).orElseThrow(() -> new ResourceNotFoundException("Default address not found with user: " + user.getUsername()));
        return AddressMapper.toDto(address);
    }

}