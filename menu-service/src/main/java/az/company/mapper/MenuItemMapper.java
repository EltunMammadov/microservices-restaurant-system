package az.company.mapper;

import az.company.dao.entity.MenuItemEntity;
import az.company.model.request.CreateMenuItemRequest;
import az.company.model.response.MenuItemResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MenuItemMapper {
    public static MenuItemEntity toEntity(CreateMenuItemRequest request) {
        return MenuItemEntity.builder()
                .restaurantId(request.getRestaurantId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .isAvailable(request.getIsAvailable())
                .category(request.getCategory())
                .build();
    }

    public static MenuItemResponse toResponse(MenuItemEntity entity) {
        return MenuItemResponse.builder()
                .id(entity.getId())
                .restaurantId(entity.getRestaurantId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .isAvailable(entity.getIsAvailable())
                .category(entity.getCategory())
                .build();
    }
}
