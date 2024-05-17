package dk.bigbowl.sales.repository;

import dk.bigbowl.sales.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
