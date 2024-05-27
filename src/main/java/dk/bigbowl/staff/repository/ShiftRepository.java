package dk.bigbowl.staff.repository;

import dk.bigbowl.staff.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
}
