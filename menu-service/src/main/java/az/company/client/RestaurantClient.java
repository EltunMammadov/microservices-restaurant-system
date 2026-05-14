package az.company.client;

import az.company.exception.ClientException;
import az.company.exception.ErrorResponse;
import az.company.model.client.RestaurantResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${client.urls.restaurant-service}")
    private String restaurantService;

    @SneakyThrows
    public void getRestaurantById(Long id) {
        log.info("ActionLog.RestaurantClient.getRestaurantById.start - id: {}", id);
        var url = restaurantService + id;
        try {
            RestaurantResponse restaurantResponse = restTemplate.getForObject(url, RestaurantResponse.class);
            log.info("ActionLog.RestaurantClient.getRestaurantById.end - id: {}, restaurant: {}", id, restaurantResponse);
        } catch (HttpStatusCodeException exception) {
            log.error("ActionLog.RestaurantClient.getRestaurantById.error - id: {}, status: {}, response: {}",
                    id, exception.getStatusCode(), exception.getResponseBodyAsString());
            var errorResponse = objectMapper.readValue(exception.getResponseBodyAsString(), ErrorResponse.class);
            throw new ClientException(errorResponse.getCode(), errorResponse.getMessage(), exception.getStatusCode().value());
        }
    }
}
