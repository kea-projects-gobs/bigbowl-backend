package dk.bigbowl.reservation.repository;

import dk.bigbowl.reservation.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long>
{
    Optional<Activity> findByTypeName(String typeName);
    Optional<Activity> findByName(String name);
}
