package az.company.service.impl;

import az.company.client.RestaurantClient;
import az.company.dao.repository.MenuItemRepository;
import az.company.exception.NotFoundException;
import az.company.mapper.MenuItemMapper;
import az.company.model.request.CreateMenuItemRequest;
import az.company.model.response.MenuItemResponse;
import az.company.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantClient restaurantClient;

    @Override
    public void createMenuItem(CreateMenuItemRequest request) {
        log.info("ActionLog.createMenuItem.start - request: {}", request);
        restaurantClient.getRestaurantById(request.getRestaurantId());
        var entity = MenuItemMapper.toEntity(request);
        menuItemRepository.save(entity);
        log.info("ActionLog.createMenuItem.end - menuItemId: {}", entity.getId());
    }

    @Override
    public List<MenuItemResponse> getAllMenuItemsByRestaurantId(Long restaurantId) {
        log.info("ActionLog.getAllMenuItemsByRestaurantId.start - restaurantId: {}", restaurantId);
        var menuItems = menuItemRepository.findByRestaurantId(restaurantId);
        var menuItemsResponse = menuItems.stream()
                .map(MenuItemMapper::toResponse)
                .toList();
        log.info("ActionLog.getAllMenuItemsByRestaurantId.end - totalMenuItems: {}", menuItemsResponse.size());
        return menuItemsResponse;
    }

    @Override
    public MenuItemResponse getMenuItemById(Long id) {
        log.info("ActionLog.getMenuItemById.start - id: {}", id);
        var menuItemEntity = menuItemRepository.findById(id)
                .orElseThrow(() -> {
                   log.error(id + "ActionLog.getMenuItemById.error - id: {}", id);
                   return new NotFoundException("Menu Item not found with id: " + id);
                });
        var response = MenuItemMapper.toResponse(menuItemEntity);
        log.info("ActionLog.getMenuItemById.end - menuItem: {}", response);
        return response;
    }

    @Override
    public void deleteMenuItem(Long id) {
        log.info("ActionLog.deleteMenuItem.start - id: {}", id);
        menuItemRepository.deleteById(id);
        log.info("ActionLog.deleteMenuItem.end - id: {}", id);
    }
}
