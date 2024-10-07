package com.electroboy.ecommerce.order;

import com.electroboy.ecommerce.customer.CustomerClient;
import com.electroboy.ecommerce.exception.BusinessException;
import com.electroboy.ecommerce.kafka.OrderConfirmation;
import com.electroboy.ecommerce.kafka.OrderProducer;
import com.electroboy.ecommerce.orderline.OrderLineRequest;
import com.electroboy.ecommerce.orderline.OrderLineService;
import com.electroboy.ecommerce.payment.PaymentClient;
import com.electroboy.ecommerce.payment.PaymentRequest;
import com.electroboy.ecommerce.product.ProductClient;
import com.electroboy.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    @Transactional
    public Integer createOrder(OrderRequest request) {
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID" + request.customerId()));

        var purchasedProducts = productClient.purchaseProducts(request.products());

        var order = orderRepository.save(orderMapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse findOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toOrderResponse)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", orderId)));
    }
}
