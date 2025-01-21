package demo.eda.service;

import demo.eda.event.OrderCreatedEvent;
import demo.eda.model.Order;
import demo.eda.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ReactiveKafkaProducerTemplate<String, OrderCreatedEvent> producer;

    @Test
    void should_PersistOrder_When_CreatingOrder() {
        final Order order = new Order(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                42d,
                "ORDERED"
        );
        when(orderRepository.save(any(Order.class))).thenReturn(Mono.just(order));
        when(producer.send(anyString(), any(OrderCreatedEvent.class))).thenReturn(Mono.empty());

        orderService.create(Mono.just(order)).block();

        verify(orderRepository, atLeastOnce()).save(any(Order.class));
    }
}
