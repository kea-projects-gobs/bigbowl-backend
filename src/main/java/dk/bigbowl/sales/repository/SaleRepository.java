package dk.bigbowl.sales.repository;

import dk.bigbowl.sales.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long>{
}
