package demo.eda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    private String userId;
    private double amount;
    private String status;
}
