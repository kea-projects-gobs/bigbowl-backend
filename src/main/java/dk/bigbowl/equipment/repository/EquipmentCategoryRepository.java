package dk.bigbowl.equipment.repository;

import dk.bigbowl.equipment.entity.EquipmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentCategoryRepository extends JpaRepository<EquipmentCategory, String>{
}
