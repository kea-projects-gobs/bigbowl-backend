package dk.bigbowl.sales.service.impl;

import dk.bigbowl.sales.dto.ProductDTO;
import dk.bigbowl.sales.dto.SaleDTO;
import dk.bigbowl.sales.dto.SalesItemDTO;
import dk.bigbowl.sales.entity.Product;
import dk.bigbowl.sales.entity.Sale;
import dk.bigbowl.sales.entity.SalesItem;
import dk.bigbowl.sales.repository.ProductRepository;
import dk.bigbowl.sales.repository.SaleRepository;
import dk.bigbowl.sales.service.ProductService;
import dk.security.entity.UserWithRoles;
import dk.security.repository.UserWithRolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SaleServiceImplTest {

    @Mock
    private UserWithRolesRepository userWithRolesRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductService productService;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private Principal principal;

    @InjectMocks
    private SaleServiceImpl saleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSales() {
        // Arrange
        // Use the method to create a Sale
        Sale sale = createTestSale();  
        when(saleRepository.findAll()).thenReturn(Collections.singletonList(sale));

        // Act
        List<SaleDTO> result = saleService.getAllSales();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getId());
        verify(saleRepository).findAll();
    }

    @Test
    void testGetSaleById_Found() {
        // Arrange
        Long saleId = 1L;
        Sale sale = createTestSale();
        when(saleRepository.findById(saleId)).thenReturn(Optional.of(sale));

        // Act
        SaleDTO result = saleService.getSaleById(saleId);

        // Assert
        assertNotNull(result);
        verify(saleRepository).findById(saleId);
    }

    @Test
    void testGetSaleById_NotFound() {
        // Arrange
        Long saleId = 1L;
        when(saleRepository.findById(saleId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            saleService.getSaleById(saleId);
        });

        assertEquals("Sale not found", exception.getMessage());
        verify(saleRepository).findById(saleId);
    }


    @Test
    void testCreateSale() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setPrice(19.99);

        SalesItemDTO salesItemDTO = new SalesItemDTO();
        salesItemDTO.setProduct(new ProductDTO(productId, "Test Product", 10, 19.99, "Description", "image.jpg", true, "Category"));
        salesItemDTO.setQuantity(2);
        salesItemDTO.setUnitPrice(19.99);

        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setSalesItems(Collections.singletonList(salesItemDTO));

        Sale sale = new Sale();
        SalesItem salesItem = new SalesItem();
        salesItem.setProduct(product);
        salesItem.setQuantity(2);
        salesItem.setUnitPrice(19.99);
        sale.setSalesItems(Collections.singletonList(salesItem));

        when(principal.getName()).thenReturn("username");
        when(userWithRolesRepository.findByUsername("username")).thenReturn(Optional.of(new UserWithRoles()));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);

        // Act
        SaleDTO result = saleService.createSale(saleDTO, principal);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getSalesItems());
        assertFalse(result.getSalesItems().isEmpty());
        assertEquals(2, result.getSalesItems().get(0).getQuantity());
        verify(saleRepository).save(any(Sale.class));
        verify(productRepository).findById(productId);
    }


    // Helper method for creation of sales:
    private Sale createTestSale() {
        // Create UserWithRoles instance
        UserWithRoles employee = new UserWithRoles();
        employee.setUsername("testEmployee");
    
        // Create SalesItem instances
        SalesItem item1 = new SalesItem();
        item1.setId(1L);
        item1.setQuantity(5);
    
        SalesItem item2 = new SalesItem();
        item2.setId(2L);
        item2.setQuantity(3);
    
        // Create Sale instance
        Sale sale = new Sale();
        sale.setId(1L);
        sale.setDate(LocalDateTime.now());
        sale.setSalesItems(Arrays.asList(item1, item2));
        sale.setEmployee(employee);
    
        return sale;
    }

}