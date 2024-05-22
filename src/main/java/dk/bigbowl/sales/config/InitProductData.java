package dk.bigbowl.sales.config;

import dk.bigbowl.sales.entity.Product;
import dk.bigbowl.sales.entity.ProductCategory;
import dk.bigbowl.sales.repository.ProductCategoryRepository;
import dk.bigbowl.sales.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class InitProductData implements CommandLineRunner {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;

    public InitProductData(ProductCategoryRepository productCategoryRepository, ProductRepository productRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        InitProductsAndCategories();
    }


    public void InitProductsAndCategories(){

        ProductCategory category1 = new ProductCategory("Alcohol");
        ProductCategory category2 = new ProductCategory("Soft drinks");

        List<ProductCategory> categories = Arrays.asList(category1, category2);
        productCategoryRepository.saveAll(categories);


        Product product1 = new Product("Heineken", 100, 45, "500 ml", "https://images.liquorapps.com/CanadaSite/bg/361841-HEINEKEN-500ML-CAN19.png", true, category1);
        Product product2 = new Product("Coca cola", 1000, 30, "500 ml", "https://www.coca-cola.com/content/dam/onexp/dk/da/home-images/brands-images/coca-cola/DK_coca-cola-original-taste-danmark_750x750.jpg", true, category2);
        Product product3 = new Product("Fanta", 1000, 30, "500 ml", "https://shop11330.sfstatic.io/upload_dir/shop/_thumbs/fanta.w610.h610.fill.png", true, category2);
        Product product4 = new Product("Sprite", 1000, 30, "500 ml", "https://www.drikkevarer.nu/wp-content/uploads/2023/07/54492028-Sprite.jpg", true, category2);
        Product product5 = new Product("Tuborg Classic", 1000, 45, "500 ml", "https://imageproxy.wolt.com/menu/menu-images/638742b55164159fc1172fb9/b202499c-7ac4-11ed-b31c-c69d2376b6ad_5740700997075.jpeg", true, category1);

        List<Product> products = Arrays.asList(product1, product2, product3, product4, product5);
        productRepository.saveAll(products);
    }
}
