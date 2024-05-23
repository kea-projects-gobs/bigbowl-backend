package dk.bigbowl.equipment.config;

import dk.bigbowl.equipment.entity.Equipment;
import dk.bigbowl.equipment.entity.EquipmentCategory;
import dk.bigbowl.equipment.repository.EquipmentCategoryRepository;
import dk.bigbowl.equipment.repository.EquipmentRepository;
import dk.bigbowl.equipment.service.EquipmentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitEquipmentData implements CommandLineRunner {

    private final EquipmentCategoryRepository equipmentCategoryRepository;
    private final EquipmentRepository equipmentRepository;

    public InitEquipmentData(EquipmentCategoryRepository equipmentCategoryRepository, EquipmentRepository equipmentRepository) {
        this.equipmentCategoryRepository = equipmentCategoryRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initEquipmentCategories();
        initEquipment();
    }

    private void initEquipmentCategories() {
        EquipmentCategory bowlingCategory = new EquipmentCategory("Bowling");
        equipmentCategoryRepository.save(bowlingCategory);
    }

    private void initEquipment() {
        EquipmentCategory bowlingCategory = equipmentCategoryRepository.findById("Bowling").orElse(null);
        if (bowlingCategory != null) {
            Equipment bowlingPins = new Equipment(null, "Bowling Pins", "Standard bowling pin", "url_to_image", 40, bowlingCategory);
            equipmentRepository.save(bowlingPins);

            Equipment bowlingShoes = new Equipment(null, "Bowling Shoes", "Standard bowling shoes", "url_to_image", 24 * 6, bowlingCategory);
            equipmentRepository.save(bowlingShoes);
        }
    }
}
