package demo.eda.controller;

import demo.eda.model.Payment;
import demo.eda.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.UUID;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private PaymentService shipmentService;

    @Test
    void should_RespondWith_StatusCode_200_When_GettingAllPayments() {
        final Payment payment = new Payment(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                true
        );

        when(shipmentService.getAll()).thenReturn(Flux.just(payment));

        webTestClient.get()
                .uri("/api/payments")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Payment.class)
                .hasSize(1)
                .contains(payment);
    }
}
