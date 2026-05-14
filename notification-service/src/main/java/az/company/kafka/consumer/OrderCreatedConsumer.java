package az.company.kafka.consumer;

import az.company.exception.ConsumerException;
import az.company.model.dto.OrderCreatedEvent;
import az.company.service.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Constants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCreatedConsumer {
    private final MailService mailService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-created", groupId = "notification-group")
    public void consume(String message) {
        log.info("ActionLog.OrderCreatedConsumer.consume.start -  message: {}", message);
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();

        try {
            orderCreatedEvent = objectMapper.readValue(message, OrderCreatedEvent.class);
            mailService.sendMail("Order " + orderCreatedEvent.getOrderId() + " has been created");
        } catch (JsonProcessingException exception) {
            log.error("ActionLog.OrderCreatedConsumer.consume.error", exception);
            throw new ConsumerException(exception.getMessage());
        }
        log.info("ActionLog.OrderCreatedConsumer.success -  message: {}", message);
    }
}
