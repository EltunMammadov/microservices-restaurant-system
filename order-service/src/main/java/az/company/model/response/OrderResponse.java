package az.company.model.response;

import az.company.model.enums.OrderStatus;
import az.company.model.request.OrderItemRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long restaurantId;
    private List<OrderItemRequest> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
}
