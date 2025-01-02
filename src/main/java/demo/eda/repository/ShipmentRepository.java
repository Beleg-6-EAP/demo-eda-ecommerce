package demo.eda.repository;

import demo.eda.model.Shipment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends ReactiveCrudRepository<Shipment, String> {
}
