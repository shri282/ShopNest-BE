package com.shri.ShopNest.payment;

import com.shri.ShopNest.cart.model.Cart;
import com.shri.ShopNest.cart.model.CartItem;
import com.shri.ShopNest.cart.repo.CartRepo;
import com.shri.ShopNest.payment.dto.StripeResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/user/payment")
public class StripePaymentGatewayDemo {

    private final CartRepo cartRepo;
    public StripePaymentGatewayDemo(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    @GetMapping("/checkout-session/{cartId}")
    public StripeResponse createPaymentSession(@PathVariable Long cartId) throws StripeException {
        // Create a PaymentIntent with the order amount and currency
        Cart cart = cartRepo.getReferenceById(cartId);

        List<SessionCreateParams.LineItem> lineItems = createLineItems(cart);

        // Create new session with the line items
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:3001/user/cart")
                        .setCancelUrl("http://localhost:3001/user/cart")
                        .addAllLineItem(lineItems)
                        .build();

        // Create new session
        Session session = Session.create(params);

        return StripeResponse
                .builder()
                .status(200)
                .message("Payment session created")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }

    private List<SessionCreateParams.LineItem> createLineItems(Cart cart) {

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        for (CartItem cartItem: cart.getItems()) {
            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(cartItem.getProduct().getName())
                            .setDescription(cartItem.getProduct().getDescription())
                            .build();

            // Create new line item with the above product data and associated price
            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("INR")
                            .setUnitAmount(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(100)).longValue())
                            .setProductData(productData)
                            .build();

            // Create new line item with the above price data
            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams
                            .LineItem.builder()
                            .setQuantity(cartItem.getQuantity().longValue())
                            .setPriceData(priceData)
                            .build();

            lineItems.add(lineItem);
        }

        return lineItems;
    }


}
