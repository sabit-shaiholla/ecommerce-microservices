package com.electroboy.ecommerce.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Service API", description = "Customer management API")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @Operation(summary = "Create a new customer", description = "Create a new customer with the provided details")
    @ApiResponse(responseCode = "200", description = "Customer created successfully")
    public ResponseEntity<String> createCustomer(
            @RequestBody @Valid CustomerRequest request
    ) {
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @PutMapping
    @Operation(summary = "Update an existing customer", description = "Update an existing customer with the provided details")
    @ApiResponse(responseCode = "202", description = "Customer updated successfully")
    public ResponseEntity<Void> updateCustomer(
            @RequestBody @Valid CustomerRequest request
    ) {
        customerService.updateCustomer(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    @Operation(summary = "Get all customers", description = "Retrieves the list of all customers")
    @ApiResponse(responseCode = "200", description = "List of customers retrieved successfully")
    public ResponseEntity<List<CustomerResponse>> findAllCustomers() {
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    @GetMapping("/exists/{customer-id}")
    @Operation(summary = "Check if customer exists", description = "Check if a customer exists with the provided ID")
    @ApiResponse(responseCode = "200", description = "Customer exists status retrieved successfully")
    public ResponseEntity<Boolean> customerExistsById(
            @PathVariable("customer-id") String customerId
    ) {
        return ResponseEntity.ok(customerService.customerExistsById(customerId));
    }

    @GetMapping("/{customer-id}")
    @Operation(summary = "Find customer by ID", description = "Find a customer by the provided ID")
    @ApiResponse(responseCode = "200", description = "Customer retrieved successfully")
    public ResponseEntity<CustomerResponse> findCustomerById(
            @PathVariable("customer-id") String customerId
    ) {
        return ResponseEntity.ok(customerService.findById(customerId));
    }

    @DeleteMapping("/{customer-id}")
    @Operation(summary = "Delete customer by ID", description = "Delete a customer by the provided ID")
    @ApiResponse(responseCode = "202", description = "Customer deleted successfully")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable("customer-id") String customerId
    ) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }
}
