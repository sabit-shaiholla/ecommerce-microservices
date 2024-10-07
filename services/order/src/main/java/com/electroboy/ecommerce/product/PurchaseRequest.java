package com.electroboy.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Validated
public record PurchaseRequest(
        @NotNull(message = "Product ID is required")
        Integer productId,
        @Positive(message = "Quantity should be greater than zero")
        double quantity
) {
}
