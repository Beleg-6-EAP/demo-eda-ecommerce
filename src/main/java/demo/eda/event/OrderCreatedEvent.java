package demo.eda.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    public static final String TOPIC = "order-events";

    private String orderId;
    private String userId;
    private double amount;
}
