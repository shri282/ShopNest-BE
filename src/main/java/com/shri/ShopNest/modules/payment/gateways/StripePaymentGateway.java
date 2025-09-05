package com.shri.ShopNest.modules.payment.gateways;

import com.shri.ShopNest.modules.payment.PaymentGateway;
import com.shri.ShopNest.modules.payment.dto.PaymentRequestDto;
import com.shri.ShopNest.modules.payment.dto.PaymentResponseDto;
import com.shri.ShopNest.modules.payment.dto.RefundRequestDto;
import com.shri.ShopNest.modules.payment.dto.RefundResponseDto;

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
