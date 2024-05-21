package dk.bigbowl.sales.service.impl;

import dk.bigbowl.sales.dto.ProductCategoryDTO;
import dk.bigbowl.sales.entity.ProductCategory;
import dk.bigbowl.sales.repository.ProductCategoryRepository;
import dk.bigbowl.sales.service.ProductCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<ProductCategoryDTO> getAllProductCategories() {
        return productCategoryRepository.findAll().stream()
        .map(category -> new ProductCategoryDTO(category.getName()))
        .collect(Collectors.toList());
    }
}
