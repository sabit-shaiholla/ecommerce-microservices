package com.electroboy.ecommerce.kafka;

import com.electroboy.ecommerce.customer.CustomerResponse;
import com.electroboy.ecommerce.order.PaymentMethod;
import com.electroboy.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal orderAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
