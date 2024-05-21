package dk.bigbowl.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesItemDTO {
    private Long id;
    private ProductDTO product;
    private int quantity;
    private double unitPrice;
}
