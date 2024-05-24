package dk.bigbowl.reservation.repository;

import dk.bigbowl.reservation.entity.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationItemRepository extends JpaRepository<ReservationItem, Long> {
    List<ReservationItem> findByActivityIdAndStartTimeLessThanAndEndTimeGreaterThan(Long activityId, LocalDateTime startTime, LocalDateTime endTime);
    List<ReservationItem> findByActivityIdAndStartTime(Long activityId, LocalDateTime date);
    @Query("SELECT r FROM ReservationItem r WHERE " +
            "(:startTime < r.endTime AND :endTime > r.startTime) and r.activity.type.name = :activityType")
    List<ReservationItem> findOverlappingReservations(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("activityType") String activityType);
}
