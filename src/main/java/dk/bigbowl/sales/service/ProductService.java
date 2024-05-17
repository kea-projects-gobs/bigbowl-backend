package dk.bigbowl.sales.service;

import dk.bigbowl.sales.dto.ProductDTO;
import dk.bigbowl.sales.entity.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO convertToDTO(Product product);
    Product convertToEntity(ProductDTO productDTO);
}
