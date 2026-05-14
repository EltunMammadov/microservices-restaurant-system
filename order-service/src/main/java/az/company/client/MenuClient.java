package az.company.client;

import az.company.exception.ClientException;
import az.company.exception.ErrorResponse;
import az.company.model.client.MenuItemDto;
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
public class MenuClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${client.urls.menu-service}")
    private String menuServiceUrl;

    @SneakyThrows
    public MenuItemDto getMenuItemById(Long id) {
        log.info("ActionLog.MenuClient.getMenuItemById.start - id: {}", id);
        var url = String.format(menuServiceUrl + "%d", id);
        try {
            var menuItem = restTemplate.getForObject(url, MenuItemDto.class);
            log.info("ActionLog.MenuClient.getMenuItemById.success id: {}, menuItem: {}", id, menuItem);
            return menuItem;
        } catch (HttpStatusCodeException exception) {
            log.error("ActionLog.MenuClient.getMenuItemById.error - id: {}", id);
            var errorResponse = objectMapper.readValue(exception.getResponseBodyAsString(), ErrorResponse.class);

            throw new ClientException(
                    errorResponse.getCode(),
                    errorResponse.getMessage(),
                    exception.getStatusCode().value()
            );
        }
    }
}

