package com.shri.ShopNest.schedulers;


import com.shri.ShopNest.cart.service.CartService;
import com.shri.ShopNest.utils.EmailService;
import org.springframework.stereotype.Service;

@Service
public class CartScheduler {

    private final CartService cartService;

    private final EmailService emailService;

    public CartScheduler(CartService cartService, EmailService emailService) {
        this.cartService = cartService;
        this.emailService = emailService;
    }

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
