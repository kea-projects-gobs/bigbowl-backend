package dk.security.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee extends UserWithRoles{

    LocalDate startDate;

    LocalDate endDate;

    int hourlyWage;

    public Employee(String username, String password, String email, String name, String address, LocalDate startDate, LocalDate endDate, int hourlyWage) {
        super(username, password, email, name, address);
        this.startDate = startDate;
        this.endDate = endDate;
        this.hourlyWage = hourlyWage;
    }

}
