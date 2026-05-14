package az.company.service.impl;

import az.company.dao.entity.DeliveryEntity;
import az.company.dao.repository.DeliveryRepository;
import az.company.kafka.KafkaDeliveryProducer;
import az.company.model.dto.OrderConfirmedEvent;
import az.company.model.dto.OrderDeliveredEvent;
import az.company.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

import static az.company.model.enums.DeliveryStatus.DELIVERED;
import static az.company.model.enums.DeliveryStatus.IN_PROGRESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final KafkaDeliveryProducer kafkaDeliveryProducer;

    @Override
    public void processDelivery(OrderConfirmedEvent event) {
        log.info("ActionLog.processDelivery.start - event: {}", event);
        var deliveryEntity = new DeliveryEntity();
        deliveryEntity.setOrderId(event.getOrderId());
        deliveryEntity.setRestaurantId(event.getRestaurantId());
        deliveryEntity.setStatus(IN_PROGRESS);
        deliveryRepository.save(deliveryEntity);

        new Thread(() -> {
            try {
                Thread.sleep(Duration.ofSeconds(30).toMillis());
                deliveryEntity.setStatus(DELIVERED);
                deliveryEntity.setDeliveredAt(LocalDateTime.now());
                deliveryRepository.save(deliveryEntity);

                var orderDeliveredEvent = new OrderDeliveredEvent();
                orderDeliveredEvent.setOrderId(event.getOrderId());
                orderDeliveredEvent.setRestaurantId(event.getRestaurantId());

                kafkaDeliveryProducer.publishOrderDelivered(orderDeliveredEvent);
            } catch (InterruptedException exception) {
                log.error("Delivery processing interrupted", exception);
                Thread.currentThread().interrupt();
            }
        }).start();

        log.info("ActionLog.processDelivery.end - event: {}", event);
    }
}
