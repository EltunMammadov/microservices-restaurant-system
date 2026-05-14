package az.company.kafka;

import az.company.model.dto.OrderConfirmedEvent;
import az.company.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaDeliveryConsumer {
    private final DeliveryService deliveryService;

    @KafkaListener(topics = "order-confirmed", groupId = "delivery-group")
    public void consumerOrderConfirmed(OrderConfirmedEvent event) {
        log.info("ActionLog.consumerOrderConfirmed.start - event: {}", event);
        deliveryService.processDelivery(event);
        log.info("ActionLog.consumerOrderConfirmed.end - event: {}", event);
    }
}
