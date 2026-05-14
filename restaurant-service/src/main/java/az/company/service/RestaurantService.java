package az.company.service;

import az.company.model.request.CreateRestaurantRequest;
import az.company.model.response.RestaurantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {
    void createRestaurant(CreateRestaurantRequest request);

    Page<RestaurantResponse> getAllRestaurants(Pageable pageable);

    RestaurantResponse getRestaurantById(Long id);

    void deleteRestaurant(Long id);
}
