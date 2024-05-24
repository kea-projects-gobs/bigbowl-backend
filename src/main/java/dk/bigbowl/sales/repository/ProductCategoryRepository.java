package dk.bigbowl.sales.repository;

import dk.bigbowl.sales.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    ProductCategory findByName(String name);
}
