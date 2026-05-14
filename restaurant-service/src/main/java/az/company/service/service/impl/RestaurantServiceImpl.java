package az.company.service.service.impl;

import az.company.dao.repository.RestaurantRepository;
import az.company.exception.NotFoundException;
import az.company.mapper.RestaurantMapper;
import az.company.model.request.CreateRestaurantRequest;
import az.company.model.response.RestaurantResponse;
import az.company.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Override
    public void createRestaurant(CreateRestaurantRequest request) {
        log.info("ActionLog.createRestaurant.start - request: {}", request);
        var entity = RestaurantMapper.toEntity(request);
        restaurantRepository.save(entity);
        log.info("ActionLog.createRestaurant.end - restaurantId: {}", entity.getId());
    }

    @Override
    public Page<RestaurantResponse> getAllRestaurants(Pageable pageable) {
        log.info("ActionLog.getAllRestaurants.start");
        var restaurantPage = restaurantRepository.findAll(pageable);
        log.info("ActionLog.getAllRestaurants.end - totalRestaurants: {}", restaurantPage.getTotalElements());
        return restaurantPage.map(RestaurantMapper::toResponse);
    }

    @Override
    public RestaurantResponse getRestaurantById(Long id) {
        log.info("ActionLog.getRestaurantById.start - id: {}", id);
        var restaurantEntity = restaurantRepository.findById(id)
                .orElseThrow(() ->
                {
                    log.error("ActionLog.getRestaurantById.error - id: {}", id);
                    return new NotFoundException("Restaurant not found with id: " + id);
                });
        log.info("ActionLog.getRestaurantById.end - restaurant: {}", restaurantEntity);
        return RestaurantMapper.toResponse(restaurantEntity);
    }

    @Override
    public void deleteRestaurant(Long id) {
        log.info("ActionLog.deleteRestaurant.start - id: {}", id);
        var restaurantEntity = restaurantRepository.findById(id)
                .orElseThrow(() ->
                {
                   log.error("ActionLog.deleteRestaurant.error - id: {}", id);
                   return new NotFoundException("Restaurant not found with id: " + id);
                });
        restaurantRepository.delete(restaurantEntity);
        log.info("ActionLog.deleteRestaurant.end - id: {}", id);
    }
}
