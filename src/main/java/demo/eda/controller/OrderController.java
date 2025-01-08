package demo.eda.controller;

import demo.eda.model.Order;
import demo.eda.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Mono<Void>> createOrder(@RequestBody Mono<Order> orderMono) {
        return new ResponseEntity<>(orderService.create(orderMono).then(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Flux<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAll());
    }
}
