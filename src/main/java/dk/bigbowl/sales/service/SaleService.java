package dk.bigbowl.sales.service;

import dk.bigbowl.sales.dto.SaleDTO;
import dk.bigbowl.sales.entity.Sale;

import java.security.Principal;
import java.util.List;

public interface SaleService {
    SaleDTO createSale(SaleDTO saleDTO, Principal principal);
    List<SaleDTO> getAllSales();
    SaleDTO getSaleById(Long id);

    SaleDTO convertToDTO(Sale sale);

    Sale convertToEntity(SaleDTO saleDTO, Principal principal);
}
