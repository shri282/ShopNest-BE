package com.shri.ShopNest.payment.gateways;

import com.shri.ShopNest.payment.PaymentGateway;
import com.shri.ShopNest.payment.dto.PaymentRequestDto;
import com.shri.ShopNest.payment.dto.PaymentResponseDto;
import com.shri.ShopNest.payment.dto.RefundRequestDto;
import com.shri.ShopNest.payment.dto.RefundResponseDto;

public class StripePaymentGateway implements PaymentGateway {
    @Override
    public PaymentResponseDto processPayment(PaymentRequestDto request) {
        return null;
    }

    @Override
    public RefundResponseDto refundPayment(RefundRequestDto request) {
        return null;
    }

    @Override
    public String getGatewayName() {
        return null;
    }
}
