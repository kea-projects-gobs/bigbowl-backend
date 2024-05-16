package dk.bigbowl.reservation.repository;

import dk.bigbowl.reservation.entity.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationItemRepository extends JpaRepository<ReservationItem, Long> {
    List<ReservationItem> findByActivityIdAndStartTimeLessThanAndEndTimeGreaterThan(Long activityId, LocalDateTime startTime, LocalDateTime endTime);
}
