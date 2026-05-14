package az.company.controller;

import az.company.model.request.CreateOrderRequest;
import az.company.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/order/v1")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createOrder(@RequestBody CreateOrderRequest request) {
        orderService.createOrder(request);
    }

    @PostMapping("/{id}/confirm")
    @ResponseStatus(CREATED)
    public void confirm(@PathVariable(name = "id") Long id) {
        orderService.confirmOrder(id);
    }

}
