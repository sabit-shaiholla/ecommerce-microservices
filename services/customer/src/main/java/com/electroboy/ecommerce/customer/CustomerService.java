package com.electroboy.ecommerce.customer;

import com.electroboy.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest request) {
        var customer = customerRepository.save(customerMapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        var customer = customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Customer with id %s not found", request.id())
                ));
        mergeCustomer(customer, request);
        customerRepository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstName())) {
            customer.setFirstName(request.firstName());
        }
        if(StringUtils.isNotBlank(request.lastName())) {
            customer.setLastName(request.lastName());
        }
        if(StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if(request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toCustomerResponse)
                .collect(Collectors.toList());
    }

    public Boolean customerExistsById(String customerId) {
        return customerRepository.findById(customerId)
                .isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::toCustomerResponse)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Customer with id %s not found", customerId)
                ));
    }

    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
