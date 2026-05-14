package az.company.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaHelper {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, Object message) {
        log.info("Sending message to Kafka topic: {}, message: {}", topic, message);
        kafkaTemplate.send(topic, message);
        log.info("Message sent to Kafka topic: {}", topic);
    }
}
