package dk.bigbowl.reservation.repository;

import dk.bigbowl.reservation.entity.Reservation;
import dk.security.entity.UserWithRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUser(UserWithRoles user);
    @Query("SELECT r FROM Reservation r WHERE r.date >= :from AND r.date <= :to")
    List<Reservation> findByDateRange(LocalDate from, LocalDate to);
}
