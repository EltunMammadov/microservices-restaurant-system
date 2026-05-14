package az.company.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaDeliveryProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publishOrderDelivered(Object message) {
        log.info("ActionLog.publishOrderDelivered.start - event: {}", message);
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            kafkaTemplate.send("order-delivered", jsonMessage);
            log.info("ActionLog.publishOrderDelivered.end - event: {}", message);
        } catch (Exception e) {
            log.error("Error publishing OrderDeliveredEvent: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to publish OrderDeliveredEvent", e);
        }
    }
}
