
package dk.bigbowl.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int stock;
    private double price;
    private String description;
    private String imageUrl;
    private boolean isActive;

    @ManyToOne
    private ProductCategory productCategory;

    public Product(String name, int stock, double price, String description, String imageUrl, boolean isActive, ProductCategory productCategory) {
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
        this.productCategory = productCategory;
    }
}
