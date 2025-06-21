package com.shri.ShopNest.payment;

import com.shri.ShopNest.payment.dto.PaymentRequestDto;
import com.shri.ShopNest.payment.dto.PaymentResponseDto;
import com.shri.ShopNest.payment.dto.RefundRequestDto;
import com.shri.ShopNest.payment.dto.RefundResponseDto;

public interface PaymentGateway {
    PaymentResponseDto processPayment(PaymentRequestDto request);
    RefundResponseDto refundPayment(RefundRequestDto request);
    String getGatewayName(); // Optional: for identifying the gateway
}
