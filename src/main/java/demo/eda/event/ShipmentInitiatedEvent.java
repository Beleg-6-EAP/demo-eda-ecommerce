package demo.eda.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShipmentInitiatedEvent {

    public static final String TOPIC = "shipment-events";

    private String shipmentId;
    private String orderId;
    private String trackingId;
}
