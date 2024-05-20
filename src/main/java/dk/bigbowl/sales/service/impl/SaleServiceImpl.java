package dk.bigbowl.sales.service.impl;

import dk.bigbowl.sales.dto.SaleDTO;
import dk.bigbowl.sales.dto.SalesItemDTO;
import dk.bigbowl.sales.entity.Sale;
import dk.bigbowl.sales.entity.SalesItem;
import dk.bigbowl.sales.repository.ProductRepository;
import dk.bigbowl.sales.repository.SaleRepository;
import dk.bigbowl.sales.service.ProductService;
import dk.bigbowl.sales.service.SaleService;
import dk.security.repository.UserWithRolesRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final UserWithRolesRepository userWithRolesRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final SaleRepository saleRepository;

    public SaleServiceImpl(UserWithRolesRepository userWithRolesRepository, ProductRepository productRepository, ProductService productService, SaleRepository saleRepository) {
        this.userWithRolesRepository = userWithRolesRepository;
        this.productRepository = productRepository;
        this.productService = productService;
        this.saleRepository = saleRepository;
    }

    @Override
    public SaleDTO createSale(SaleDTO saleDTO, Principal principal) {
        Sale sale = convertToEntity(saleDTO, principal);
        saleRepository.save(sale);
        return convertToDTO(sale);
    }

    @Override
    public List<SaleDTO> getAllSales() {
        return null;
    }

    @Override
    public SaleDTO getSaleById(Long id) {
        return null;
    }

    @Override
    public SaleDTO convertToDTO(Sale sale) {
        SaleDTO dto = new SaleDTO();
        dto.setId(sale.getId());
        dto.setDate(sale.getDate());
        dto.setEmployee(sale.getEmployee().getUsername());


        List<SalesItemDTO> salesItemDTOs = sale.getSalesItems().stream()
                .map(this::convertToSalesItemDTO)
                .collect(Collectors.toList());
        dto.setSalesItems(salesItemDTOs);
        return dto;
    }

    @Override
    public Sale convertToEntity(SaleDTO saleDTO, Principal principal) {
        Sale sale = new Sale();
        sale.setId(saleDTO.getId());
        sale.setDate(saleDTO.getDate());
        String employee = principal.getName();
        sale.setEmployee(userWithRolesRepository.findByUsername(employee).orElseThrow(() -> new RuntimeException("User not found")));

        List<SalesItem> salesItems = saleDTO.getSalesItems().stream()
                .map(this::convertToSalesItemEntity)
                .collect(Collectors.toList());
        salesItems.forEach(salesItem -> salesItem.setSale(sale));
        sale.setSalesItems(salesItems);
        return sale;
    }

    // SalesItems converters

    private SalesItem convertToSalesItemEntity(SalesItemDTO dto) {
        SalesItem salesItem = new SalesItem();
        salesItem.setId(dto.getId());
        salesItem.setProduct(productRepository.findById(dto.getProduct().getId())
            .orElseThrow(() -> new RuntimeException("Product not found")));
        salesItem.setQuantity(dto.getQuantity());
        salesItem.setUnitPrice(salesItem.getProduct().getPrice());
        return salesItem;
    }
    
    private SalesItemDTO convertToSalesItemDTO(SalesItem salesItem) {
        SalesItemDTO dto = new SalesItemDTO();
        dto.setId(salesItem.getId());
        // Maybe a little tightly coupled with the use of productService?
        dto.setProduct(productService.convertToDTO(salesItem.getProduct()));
        dto.setQuantity(salesItem.getQuantity());
        dto.setUnitPrice(salesItem.getUnitPrice());
        return dto;
    }
}
