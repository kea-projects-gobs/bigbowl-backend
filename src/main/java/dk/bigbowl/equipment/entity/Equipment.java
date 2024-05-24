package dk.bigbowl.equipment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    String imageUrl;
    int stock;

    @ManyToOne
    EquipmentCategory category;

    @Transient
    private int requiredAmount;

    public Equipment(String name, String description, String imageUrl, int stock, EquipmentCategory category) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.category = category;
    }
}
