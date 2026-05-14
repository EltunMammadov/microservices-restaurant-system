package az.company.kafka.consumer;

import az.company.exception.ConsumerException;
import az.company.model.dto.OrderDeliveredEvent;
import az.company.service.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderDeliveredConsumer {
    private final MailService mailService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-delivered", groupId = "notification-group")
    public void consume(String message) {
        log.info("ActionLog.OrderCreatedConsumer.consume.start -  message: {}", message);
        OrderDeliveredEvent orderDeliveredEvent = new OrderDeliveredEvent();
        try {
            orderDeliveredEvent = objectMapper.readValue(message, OrderDeliveredEvent.class);
        } catch (JsonProcessingException exception) {
            log.error("ActionLog.OrderDeliveredConsumer.consume.error", exception);
            throw new ConsumerException(exception.getMessage());
        }

        mailService.sendMail("Order " + orderDeliveredEvent.getOrderId() + " has been delivered");
        log.info("ActionLog.OrderCreatedConsumer.success -  message: {}", message);
    }
}
