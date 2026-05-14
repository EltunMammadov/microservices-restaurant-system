package az.company.model.request;

import az.company.model.enums.MenuCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMenuItemRequest {
    private Long restaurantId;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean isAvailable;
    private MenuCategory category;
}
