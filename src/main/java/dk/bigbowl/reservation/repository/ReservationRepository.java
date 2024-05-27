package dk.bigbowl.reservation.repository;

import dk.bigbowl.reservation.entity.Reservation;
import dk.security.entity.UserWithRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUser(UserWithRoles user);
}
