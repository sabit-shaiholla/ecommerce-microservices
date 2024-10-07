package com.electroboy.ecommerce.payment;

import com.electroboy.ecommerce.customer.CustomerResponse;
import com.electroboy.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
