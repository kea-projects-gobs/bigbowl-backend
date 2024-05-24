package dk.bigbowl.sales.repository;

import dk.bigbowl.sales.entity.SalesItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesItemRepository extends JpaRepository<SalesItem, Long>{
}
