package dk.bigbowl.reservation.repository;

import dk.bigbowl.reservation.entity.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityTypeRepository extends JpaRepository<ActivityType, String> {
}
