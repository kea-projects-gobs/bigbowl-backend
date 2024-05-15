package dk.bigbowl.reservation.entity;

import dk.security.entity.UserWithRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "reservation")
    private List<ReservationItem> items;
    private boolean isConfirmed;
    private int noOfParticipants;
    private LocalDate date;
    @ManyToOne
    private UserWithRoles user;
}
