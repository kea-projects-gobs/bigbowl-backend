package dk.bigbowl.equipment.repository;

import dk.bigbowl.equipment.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Equipment findByName(String name);
}
