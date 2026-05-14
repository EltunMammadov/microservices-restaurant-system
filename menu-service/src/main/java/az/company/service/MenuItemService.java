package az.company.service;

import az.company.model.request.CreateMenuItemRequest;
import az.company.model.response.MenuItemResponse;

import java.util.List;

public interface MenuItemService {
    void createMenuItem(CreateMenuItemRequest request);

    List<MenuItemResponse> getAllMenuItemsByRestaurantId(Long restaurantId);

    MenuItemResponse getMenuItemById(Long id);

    void deleteMenuItem(Long id);
}
