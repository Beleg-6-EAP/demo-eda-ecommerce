package demo.eda.service;

import demo.eda.event.PaymentProcessedEvent;
import demo.eda.event.ShipmentInitiatedEvent;
import demo.eda.model.Shipment;
import demo.eda.repository.ShipmentRepository;
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
public class ShipmentServiceTest {

    @InjectMocks
    private ShipmentService shipmentService;

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ReactiveKafkaProducerTemplate<String, ShipmentInitiatedEvent> producer;

    @Test
    void should_PersistShipment_When_HandlingShipping_And_PaymentSucceeded() {
        final Shipment shipment = new Shipment(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
        when(shipmentRepository.save(any(Shipment.class))).thenReturn(Mono.just(shipment));
        when(producer.send(anyString(), any(ShipmentInitiatedEvent.class))).thenReturn(Mono.empty());

        final PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent(shipment.getOrderId(), UUID.randomUUID().toString(), true);
        shipmentService.handleShipping(paymentProcessedEvent).block();

        verify(shipmentRepository, atLeastOnce()).save(any(Shipment.class));
    }

    @Test
    void should_NotPersist_When_HandlingShipping_And_PaymentFailed() {
        final Shipment shipment = new Shipment(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );

        final PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent(shipment.getOrderId(), UUID.randomUUID().toString(), false);
        shipmentService.handleShipping(paymentProcessedEvent).block();

        verify(shipmentRepository, never()).save(any(Shipment.class));
    }
}
