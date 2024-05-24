package dk.bigbowl.reservation.repository;

import dk.bigbowl.reservation.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long>
{
    //Optional<Activity> findByTypeName(String typeName);
    Optional<Activity> findByName(String name);
    // Used in attempt to automatically assign an activity to a reservation (e.g. Dinner Table 1, instead of it being specified in the request)
    //Optional<Activity> findFirstByTypeNameAndIsActiveTrue(String typeName);
    List<Activity> findAllByTypeName(String type);
}
