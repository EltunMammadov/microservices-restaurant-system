package az.company.controller;

import az.company.model.request.CreateMenuItemRequest;
import az.company.model.response.MenuItemResponse;
import az.company.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu-item/v1")
public class MenuItemController {
    private final MenuItemService menuItemService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createMenuItem(@RequestBody CreateMenuItemRequest request) {
        menuItemService.createMenuItem(request);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<MenuItemResponse> getAllMenuItemByRestaurantId(@PathVariable(name = "restaurantId") Long restaurantId) {
        return menuItemService.getAllMenuItemsByRestaurantId(restaurantId);
    }

    @GetMapping("/{id}")
    public MenuItemResponse getMenuItemById(@PathVariable(name = "id") Long id) {
        return menuItemService.getMenuItemById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteMenuItem(@PathVariable(name = "id") Long id) {
        menuItemService.deleteMenuItem(id);
    }
}
