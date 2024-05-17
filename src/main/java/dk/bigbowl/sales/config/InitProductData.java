package dk.bigbowl.sales.config;

import dk.bigbowl.sales.entity.ProductCategory;
import dk.bigbowl.sales.repository.ProductCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class InitProductData implements CommandLineRunner {

    private final ProductCategoryRepository productCategoryRepository;

    public InitProductData(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initProductCategories();

    }

    public void initProductCategories() {
        ProductCategory category1 = new ProductCategory("Alcohol");
        ProductCategory category2 = new ProductCategory("Soft drinks");

        List<ProductCategory> categories = Arrays.asList(category1, category2);
        productCategoryRepository.saveAll(categories);
    }
}
