package demo.eda.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentProcessedEvent {

    public static final String TOPIC = "payment-events";

    private String orderId;
    private String paymentId;
    private boolean success;
}
