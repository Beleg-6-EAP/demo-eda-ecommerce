package demo.eda.service;

import demo.eda.event.OrderCreatedEvent;
import demo.eda.event.PaymentProcessedEvent;
import demo.eda.model.Payment;
import demo.eda.repository.PaymentRepository;
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
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ReactiveKafkaProducerTemplate<String, PaymentProcessedEvent> producer;

    @Test
    void should_PersistPayment_When_ProcessingPayment() {
        final Payment payment = new Payment(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                true
        );
        when(paymentRepository.save(any(Payment.class))).thenReturn(Mono.just(payment));
        when(producer.send(anyString(), any(PaymentProcessedEvent.class))).thenReturn(Mono.empty());

        final OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(payment.getOrderId(), UUID.randomUUID().toString(), 42d);
        paymentService.processPayment(orderCreatedEvent).block();

        verify(paymentRepository, atLeastOnce()).save(any(Payment.class));
    }
}
