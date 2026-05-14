package az.company.dao.repository;

import az.company.dao.entity.MenuItemEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuItemRepository extends CrudRepository<MenuItemEntity, Long> {
    List<MenuItemEntity> findByRestaurantId(Long restaurantId);
}
