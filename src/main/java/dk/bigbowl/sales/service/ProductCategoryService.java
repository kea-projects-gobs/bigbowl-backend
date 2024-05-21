package dk.bigbowl.sales.service;

import dk.bigbowl.sales.dto.ProductCategoryDTO;
import dk.bigbowl.sales.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategoryDTO> getAllProductCategories();
}
