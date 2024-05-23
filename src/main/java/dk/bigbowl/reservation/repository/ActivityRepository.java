package dk.bigbowl.reservation.repository;

import dk.bigbowl.reservation.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    int countByTypeName(String typeName);
}
