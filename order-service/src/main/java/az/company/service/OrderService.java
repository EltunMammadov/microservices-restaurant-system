package az.company.service;

import az.company.model.request.CreateOrderRequest;

public interface OrderService {
    void createOrder(CreateOrderRequest createOrderRequest);

    void confirmOrder(Long orderId);
}
