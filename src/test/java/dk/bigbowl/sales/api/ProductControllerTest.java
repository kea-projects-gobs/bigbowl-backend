package dk.bigbowl.sales.api;

import dk.bigbowl.sales.dto.ProductDTO;
import dk.bigbowl.sales.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ProductController.class)
// Disable security/auth for this test
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void getAllProducts_ReturnsListOfProductDTOs() throws Exception {
        // Arrange
        List<ProductDTO> products = new ArrayList<>();
        products.add(new ProductDTO(1L, "Product 1", 10, 9.99, "Description 1", "image1.jpg", true, "Category 1"));
        products.add(new ProductDTO(2L, "Product 2", 20, 19.99, "Description 2", "image2.jpg", true, "Category 2"));
        when(productService.getAllProducts()).thenReturn(products);

        // Act & Assert
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[1].name").value("Product 2"));
    }

    @Test
    void getProductById_ExistingId_ReturnsProductDTO() throws Exception {
        // Arrange
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO(productId, "Product 1", 10, 9.99, "Description 1", "image1.jpg", true, "Category 1");
        when(productService.getProductById(productId)).thenReturn(productDTO);

        // Act & Assert
        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId.intValue()))
                .andExpect(jsonPath("$.name").value("Product 1"));
    }

    @Test
    void deleteProduct_ExistingId_ReturnsNoContent() throws Exception {
        // Arrange
        Long productId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNoContent());
    }
}