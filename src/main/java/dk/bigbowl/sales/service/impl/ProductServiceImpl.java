package dk.bigbowl.sales.service.impl;

import dk.bigbowl.sales.dto.ProductDTO;
import dk.bigbowl.sales.entity.Product;
import dk.bigbowl.sales.entity.ProductCategory;
import dk.bigbowl.sales.repository.ProductCategoryRepository;
import dk.bigbowl.sales.repository.ProductRepository;
import dk.bigbowl.sales.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        product.setActive(true);
        return convertToDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setStock(product.getStock());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        dto.setActive(product.isActive());
        dto.setProductCategory(product.getProductCategory().getName());
        return dto;
    }

    @Override
    public Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setStock(productDTO.getStock());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setImageUrl(productDTO.getImageUrl());
        product.setActive(productDTO.isActive());
        String categoryName = productDTO.getProductCategory();
        ProductCategory category = productCategoryRepository.findByName(categoryName);
        product.setProductCategory(category);
        return product;
    }
}
