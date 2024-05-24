package dk.bigbowl.sales.api;

import dk.bigbowl.sales.dto.SaleDTO;
import dk.bigbowl.sales.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO, Principal principal){
        return ResponseEntity.ok().body(saleService.createSale(saleDTO,principal));
    }
}
