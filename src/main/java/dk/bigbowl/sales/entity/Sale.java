package dk.bigbowl.sales.entity;

import dk.security.entity.UserWithRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SalesItem> salesItems;

    @ManyToOne
    private UserWithRoles employee;

}
