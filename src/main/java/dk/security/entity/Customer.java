package dk.security.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer extends UserWithRoles {

    public Customer(String username, String password, String email, String name, String address) {
        super(username, password, email, name, address);
    }

}
