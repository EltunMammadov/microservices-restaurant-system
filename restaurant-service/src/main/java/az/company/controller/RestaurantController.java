package az.company.controller;

import az.company.model.request.CreateRestaurantRequest;
import az.company.model.response.RestaurantResponse;
import az.company.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/restaurant/v1")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createRestaurant(@Valid @RequestBody CreateRestaurantRequest request) {
        restaurantService.createRestaurant(request);
    }

    @GetMapping
    public Page<RestaurantResponse> getAllRestaurants(Pageable pageable) {
        return restaurantService.getAllRestaurants(pageable);
    }

    @GetMapping("{id}")
    public RestaurantResponse getRestaurantById(@PathVariable(name = "id") Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteRestaurant(@PathVariable(name = "id") Long id) {
        restaurantService.deleteRestaurant(id);
    }
}
