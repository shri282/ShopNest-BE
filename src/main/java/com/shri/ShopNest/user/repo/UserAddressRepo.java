package com.shri.ShopNest.user.repo;

import com.shri.ShopNest.user.model.User;
import com.shri.ShopNest.user.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepo extends JpaRepository<UserAddress, Long> {

    List<UserAddress> findByUser(User user);

    Optional<UserAddress> findByUserAndIsDefaultTrue(User user);

}
