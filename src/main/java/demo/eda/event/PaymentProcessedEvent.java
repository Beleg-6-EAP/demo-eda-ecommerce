package demo.eda.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProcessedEvent {

    public static final String TOPIC = "payment-events";

    private String orderId;
    private String paymentId;
    private boolean success;
}
