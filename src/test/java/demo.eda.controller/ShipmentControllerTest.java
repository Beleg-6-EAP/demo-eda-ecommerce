package demo.eda.controller;

import demo.eda.model.Shipment;
import demo.eda.service.ShipmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.UUID;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = ShipmentController.class)
public class ShipmentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ShipmentService shipmentService;

    @Test
    void should_RespondWith_StatusCode_200_When_GettingAllShipments() {
        final Shipment shipment = new Shipment(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );

        when(shipmentService.getAll()).thenReturn(Flux.just(shipment));

        webTestClient.get()
                .uri("/api/shipments")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Shipment.class)
                .hasSize(1)
                .contains(shipment);
    }
}
