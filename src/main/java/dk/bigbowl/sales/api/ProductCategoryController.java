package dk.bigbowl.sales.api;

import dk.bigbowl.sales.dto.ProductCategoryDTO;
import dk.bigbowl.sales.service.ProductCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ProductCategoryDTO>> getAllProductCategories(){
        return ResponseEntity.ok().body(productCategoryService.getAllProductCategories());
    }
}
