package demo.eda.controller;

import demo.eda.model.Order;
import demo.eda.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Mono<ResponseEntity<Void>> createOrder(@RequestBody Mono<Order> orderMono) {
        orderService.create(orderMono);
        return Mono.just(new ResponseEntity<>(HttpStatus.CREATED));
    }
}
