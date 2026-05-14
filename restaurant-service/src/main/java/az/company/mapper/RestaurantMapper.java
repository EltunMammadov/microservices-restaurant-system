package az.company.mapper;

import az.company.dao.entity.RestaurantEntity;
import az.company.model.request.CreateRestaurantRequest;
import az.company.model.response.RestaurantResponse;
import lombok.experimental.UtilityClass;

import static java.lang.Boolean.TRUE;

@UtilityClass
public class RestaurantMapper {
    public static RestaurantEntity toEntity(CreateRestaurantRequest request) {
        return RestaurantEntity.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .isOpen(TRUE)
                .category(request.getCategory())
                .build();
    }

    public static RestaurantResponse toResponse(RestaurantEntity entity) {
        return RestaurantResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .city(entity.getCity())
                .category(entity.getCategory())
                .isOpen(entity.getIsOpen())
                .build();
    }
}
