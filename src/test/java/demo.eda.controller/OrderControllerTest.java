package demo.eda.controller;

import demo.eda.model.Order;
import demo.eda.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = OrderController.class)
public class OrderControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private OrderService shipmentService;

    @Test
    void should_RespondWith_StatusCode_200_When_GettingAllOrders() {
        final Order payment = new Order(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                42d,
                "ORDERED"
        );

        when(shipmentService.getAll()).thenReturn(Flux.just(payment));

        webTestClient.get()
                .uri("/api/orders")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Order.class)
                .hasSize(1)
                .contains(payment);
    }

    @Test
    void should_RespondWith_StatusCode_201_When_CreatingOrder() {
        final Order order = new Order(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                42d,
                "ORDERED"
        );

        when(shipmentService.create(any())).thenReturn(Mono.just(order));

        webTestClient.post()
                .uri("/api/orders")
                .body(Mono.just(order), Order.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated();
    }
}
