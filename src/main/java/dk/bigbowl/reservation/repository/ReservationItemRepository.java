package dk.bigbowl.reservation.repository;

import dk.bigbowl.reservation.entity.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationItemRepository extends JpaRepository<ReservationItem, Long> {
}
