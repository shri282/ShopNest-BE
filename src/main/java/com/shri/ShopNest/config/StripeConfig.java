package com.shri.ShopNest.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("unused")
@Configuration
public class StripeConfig {
    private final String stripeSecretKey;

    public StripeConfig(@Value("${stripe.secret.key}") String stripeSecretKey) {
        this.stripeSecretKey = stripeSecretKey;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

}
