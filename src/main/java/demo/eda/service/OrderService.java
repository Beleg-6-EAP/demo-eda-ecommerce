package demo.eda.service;

import demo.eda.event.OrderCreatedEvent;
import demo.eda.model.Order;
import demo.eda.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ReactiveKafkaProducerTemplate<String, OrderCreatedEvent> producer;

    public Mono<Order> create(Mono<Order> orderMono) {
     return orderMono.flatMap(order -> {
         final OrderCreatedEvent event = new OrderCreatedEvent(order.getId(), order.getUserId(), order.getAmount());
         log.info("OrderCreatedEvent: {}", event);
         return producer.send(OrderCreatedEvent.TOPIC, event).then(orderRepository.save(order));
     });
    }

    public Flux<Order> getAll() {
        return orderRepository.findAll();
    }
}
