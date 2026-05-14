package az.company.service.impl;

import az.company.client.MenuClient;
import az.company.dao.repository.OrderRepository;
import az.company.exception.NotFoundException;
import az.company.helper.KafkaHelper;
import az.company.mappper.OrderMapper;
import az.company.model.dto.OrderConfirmedEvent;
import az.company.model.dto.OrderCreatedEvent;
import az.company.model.enums.OrderStatus;
import az.company.model.request.CreateOrderRequest;
import az.company.model.request.OrderItemRequest;
import az.company.service.OrderService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MenuClient menuClient;
    private final KafkaHelper kafkaHelper;

    @Override
    public void createOrder(CreateOrderRequest createOrderRequest) {
        log.info("ActionLog.createOrder.start - request: {}", createOrderRequest);
        var total = BigDecimal.ZERO;
        List<Map<String, Object>> itemDetails = new ArrayList<>();

        for (OrderItemRequest item : createOrderRequest.getItems()) {
            var menuItem = menuClient.getMenuItemById(item.getMenuItemId());
            var price = menuItem.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(price);

            Map<String, Object> entry = OrderMapper.buildItemDetails(
                    item.getMenuItemId(),
                    menuItem.getName(),
                    menuItem.getPrice(),
                    item.getQuantity()
            );

            itemDetails.add(entry);
        }

        var itemsJson = new Gson().toJson(itemDetails);
        var entity = OrderMapper.toEntity(
                createOrderRequest.getRestaurantId(),
                itemsJson,
                total
        );

        var savedEntity = orderRepository.save(entity);

        var orderCreatedEvent = new OrderCreatedEvent(
                savedEntity.getId(),
                savedEntity.getRestaurantId(),
                savedEntity.getTotalAmount()
        );
        kafkaHelper.send("order-created", orderCreatedEvent);
        log.info("ActionLog.createOrder.end - orderId: {}, totalAmount: {}", savedEntity.getId(), savedEntity.getTotalAmount());
    }

    @Override
    public void confirmOrder(Long orderId) {
        log.info("ActionLog.confirmOrder.start - orderId: {}", orderId);
        orderRepository.findById(orderId)
                .ifPresentOrElse(order -> {
                    order.setStatus(OrderStatus.CONFIRMED);
                    orderRepository.save(order);
                    kafkaHelper.send("order-confirmed", new OrderConfirmedEvent(
                            order.getId(),
                            order.getRestaurantId()
                    ));
                    log.info("ActionLog.confirmOrder.end - orderId: {}, status: {}", orderId, order.getStatus());
                }, () -> {
                    log.error("ActionLog.confirmOrder.error - order not found with id: {}", orderId);
                    throw new NotFoundException("Order not found with id: " + orderId);
                });
    }
}

