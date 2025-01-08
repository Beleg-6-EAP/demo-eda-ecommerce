package demo.eda.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentInitiatedEvent {

    public static final String TOPIC = "shipment-events";

    private String shipmentId;
    private String orderId;
    private String trackingId;
}
