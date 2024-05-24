package dk.bigbowl.sales.service.impl;

import dk.bigbowl.sales.dto.ProductDTO;
import dk.bigbowl.sales.entity.Product;
import dk.bigbowl.sales.entity.ProductCategory;
import dk.bigbowl.sales.repository.ProductCategoryRepository;
import dk.bigbowl.sales.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getAllProducts_ReturnListOfProductDTOs(){
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Product 1", 10, 9.99, "Description 1", "image1.jpg", true, new ProductCategory("Category 1")));
        products.add(new Product(2L, "Product 2", 20, 19.99, "Description 2", "image2.jpg", true, new ProductCategory("Category 2")));
        when(productRepository.findAllByIsActiveTrue()).thenReturn(products);

        // Act
        List<ProductDTO> result = productService.getAllProducts();


        // Assert
        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getName());
        assertEquals("Product 2", result.get(1).getName());
    }

    @Test
    void getProductById_ExistingId_ReturnsProductDTO() {
        // Arrange
        Long productId = 1L;
        Product product = new Product(productId, "Product 1", 10, 9.99, "Description 1", "image1.jpg", true, new ProductCategory("Category 1"));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        ProductDTO result = productService.getProductById(productId);

        // Assert
        assertEquals(productId, result.getId());
        assertEquals("Product 1", result.getName());
    }

    @Test
    void getProductById_NonExistingId_ThrowsException() {
        // Arrange
        Long nonExistingId = 100L;
        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.getProductById(nonExistingId));
    }

    @Test
    void createProduct_ValidProductDTO_ReturnsCreatedProductDTO() {
        // Arrange
        ProductDTO productDTO = new ProductDTO(null, "New Product", 10, 9.99, "Description 1", "image1.jpg", true, "Category 1");
        Product savedProduct = new Product(1L, "New Product", 10, 9.99, "Description 1", "image1.jpg", true, new ProductCategory("Category 1"));
        when(productCategoryRepository.findByName("Category 1")).thenReturn(new ProductCategory("Category 1"));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        ProductDTO result = productService.createProduct(productDTO);

        // Assert
        assertNotNull(result.getId());
        assertEquals("New Product", result.getName());
    }

    @Test
    void updateProduct_ExistingId_ReturnsUpdatedProductDTO() {
        // Arrange
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO(productId, "Updated Product", 10, 9.99, "Updated Description", "updated_image.jpg", true, "Category 1");
        Product existingProduct = new Product(productId, "Product 1", 5, 4.99, "Description 1", "image1.jpg", true, new ProductCategory("Category 1"));
        Product updatedProduct = new Product(productId, "Updated Product", 10, 9.99, "Updated Description", "updated_image.jpg", true, new ProductCategory("Category 1"));
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productCategoryRepository.findByName("Category 1")).thenReturn(new ProductCategory("Category 1"));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
    
        // Act
        ProductDTO result = productService.updateProduct(productId, productDTO);
    
        // Assert
        assertEquals(productId, result.getId());
        assertEquals("Updated Product", result.getName());
        assertEquals("Updated Description", result.getDescription());
    }

    @Test
    void updateProduct_NonExistingId_ThrowsException() {
        // Arrange
        Long nonExistingId = 100L;
        ProductDTO productDTO = new ProductDTO(nonExistingId, "Updated Product", 10, 9.99, "Updated Description", "updated_image.jpg", true, "Category 1");
        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.updateProduct(nonExistingId, productDTO));
    }

    @Test
    void deleteProduct_ExistingId_SetsProductInactive() {
        // Arrange
        Long productId = 1L;
        Product existingProduct = new Product(productId, "Product 1", 5, 4.99, "Description 1", "image1.jpg", true, new ProductCategory("Category 1"));
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).save(existingProduct);
        assertFalse(existingProduct.isActive());
    }

    @Test
    void deleteProduct_NonExistingId_ThrowsException() {
        // Arrange
        Long nonExistingId = 100L;
        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.deleteProduct(nonExistingId));
    }
}