package dk.bigbowl.sales.service;

import dk.bigbowl.sales.dto.ProductDTO;
import dk.bigbowl.sales.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
    ProductDTO convertToDTO(Product product);
    Product convertToEntity(ProductDTO productDTO);
}
