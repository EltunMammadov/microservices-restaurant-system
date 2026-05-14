package az.company.service;

import az.company.model.dto.OrderConfirmedEvent;

public interface DeliveryService {

    void processDelivery(OrderConfirmedEvent event);

}
