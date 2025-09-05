package com.shri.ShopNest.modules.payment;

import com.shri.ShopNest.modules.payment.dto.PaymentRequestDto;
import com.shri.ShopNest.modules.payment.dto.PaymentResponseDto;
import com.shri.ShopNest.modules.payment.dto.RefundRequestDto;
import com.shri.ShopNest.modules.payment.dto.RefundResponseDto;

public interface PaymentGateway {
    PaymentResponseDto processPayment(PaymentRequestDto request);
    RefundResponseDto refundPayment(RefundRequestDto request);
    String getGatewayName(); // Optional: for identifying the gateway
}
