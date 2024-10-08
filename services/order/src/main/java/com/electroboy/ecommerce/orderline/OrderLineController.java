package com.electroboy.ecommerce.orderline;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-lines")
@RequiredArgsConstructor
@Tag(name = "Order Line Service API", description = "Order line management API")
public class OrderLineController {

    private final OrderLineService orderLineService;

    @GetMapping("/order/{order-id}")
    @Operation(summary = "Find order lines by order ID", description = "Find order lines by the provided order ID")
    @ApiResponse(responseCode = "200", description = "Order lines retrieved successfully")
    public ResponseEntity<List<OrderLineResponse>> findOrderLinesByOrderId(
            @PathVariable("order-id") Integer orderId
    ) {
        return ResponseEntity.ok(orderLineService.findOrderLinesByOrderId(orderId));
    }
}
