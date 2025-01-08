package demo.eda.service;

import demo.eda.event.PaymentProcessedEvent;
import demo.eda.event.ShipmentInitiatedEvent;
import demo.eda.model.Shipment;
import demo.eda.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ReactiveKafkaProducerTemplate<String, ShipmentInitiatedEvent> producer;

    @KafkaListener(topics = PaymentProcessedEvent.TOPIC, groupId = "shipping-service")
    public Mono<Void> handleShipping(PaymentProcessedEvent event) {
        if (event.isSuccess()) {
            final Shipment shipment = new Shipment(UUID.randomUUID().toString(), event.getOrderId(), UUID.randomUUID().toString());
            return shipmentRepository.save(shipment)
                    .flatMap(savedShipment -> {
                        ShipmentInitiatedEvent shippedEvent = new ShipmentInitiatedEvent(savedShipment.getId(), savedShipment.getOrderId(), savedShipment.getTrackingId());
                        return producer.send(ShipmentInitiatedEvent.TOPIC, shippedEvent).then();
                    });
        }
        return Mono.empty();
    }

    public Flux<Shipment> getAll() {
        return shipmentRepository.findAll();
    }
}
