package com.electroboy.ecommerce.order;

import com.electroboy.ecommerce.product.PurchaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_EMPTY)
public record OrderRequest(
        Integer id,
        String reference,

        @Positive(message = "Order amount should be greater than zero")
        BigDecimal amount,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "Customer ID is required")
        @NotEmpty(message = "Customer ID is required")
        @NotBlank(message = "Customer ID is required")
        String customerId,

        @NotEmpty(message = "Order should have at least one product")
        List<PurchaseRequest> products
) {
}
