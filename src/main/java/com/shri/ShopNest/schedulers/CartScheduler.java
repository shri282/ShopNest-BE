package com.shri.ShopNest.schedulers;


import com.shri.ShopNest.model.Cart;
import com.shri.ShopNest.model.User;
import com.shri.ShopNest.service.CartService;
import com.shri.ShopNest.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartScheduler {

    @Autowired
    private CartService cartService;

    @Autowired
    private EmailService emailService;

//    @Transactional
//    @Scheduled(cron = "*/5 * * * * *")
//    public void cartRemainder() {
//        List<Cart> carts = cartService.getAbandonedCarts();
//        System.out.format("cart remainder started for total %d abandoned carts", carts.size());
//        if (carts.isEmpty()) {
//            return;
//        }
//
//        for (Cart cart: carts) {
//            User user = cart.getUser();
//            emailService.sendEmail(user.getEmail(), "abandoned cart", "hi "+user.getUsername() +" did you forget to checkout your cart");
//        }
//
//        System.out.format("cart remainder ended for total %d abandoned carts", carts.size());
//    }

}
