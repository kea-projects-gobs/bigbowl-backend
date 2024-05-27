package dk.bigbowl.staff.repository;

import dk.bigbowl.staff.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
    @Query("SELECT s FROM Shift s WHERE s.employee.username = :username AND s.date = :date AND ((s.startTime < :endTime AND s.endTime > :startTime))")
    List<Shift> findByEmployeeAndDateAndTimeRange(
        @Param("username") String username,
        @Param("date") LocalDate date,
        @Param("startTime") LocalTime startTime,
        @Param("endTime") LocalTime endTime
    );
}
