package az.company.mappper;

import az.company.dao.entity.OrderEntity;
import az.company.model.enums.OrderStatus;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Map;

@UtilityClass
public class OrderMapper {
    public static OrderEntity toEntity(Long restaurantId, String itemsJson, BigDecimal totalAmount) {
        return OrderEntity.builder()
                .restaurantId(restaurantId)
                .itemsJson(itemsJson)
                .totalAmount(totalAmount)
                .status(OrderStatus.NEW)
                .build();
    }

    public static Map<String, Object> buildItemDetails(Long menuItemId, String name,
                                                       BigDecimal price, Integer quantity) {
        return Map.of(
                "id", menuItemId,
                "name", name,
                "price", price,
                "quantity", quantity
        );
    }
}
