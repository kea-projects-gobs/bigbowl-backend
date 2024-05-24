package dk.bigbowl.equipment.config;

import dk.bigbowl.equipment.entity.Equipment;
import dk.bigbowl.equipment.entity.EquipmentCategory;
import dk.bigbowl.equipment.repository.EquipmentCategoryRepository;
import dk.bigbowl.equipment.repository.EquipmentRepository;
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
        EquipmentCategory airHockeyCategory = new EquipmentCategory("Air hockey");
        equipmentCategoryRepository.save(bowlingCategory);
        equipmentCategoryRepository.save(airHockeyCategory);
    }

    private void initEquipment() {
        EquipmentCategory bowlingCategory = equipmentCategoryRepository.findById("Bowling").orElse(null);
        EquipmentCategory airHockeyCategory = equipmentCategoryRepository.findById("Air hockey").orElse(null);
        if (bowlingCategory != null) {
            Equipment bowlingPins = new Equipment("Bowling Kegler", "Standard bowling kegle", "url_to_image", 40, bowlingCategory);
            equipmentRepository.save(bowlingPins);

            Equipment bowlingShoes = new Equipment("Bowling Sko", "Standard bowling sko", "url_to_image", 24 * 6, bowlingCategory);
            equipmentRepository.save(bowlingShoes);

            Equipment bowlingBalls = new Equipment("Bowling Kugler", "Standard bowling kugler", "url_to_image", 95, bowlingCategory);
            equipmentRepository.save(bowlingBalls);
        }

        if (airHockeyCategory != null) {
            Equipment airHockeyPaddles = new Equipment("Air Hockey Håndtag", "Standard air hockey paddles", "url_to_image", 8, airHockeyCategory);
            equipmentRepository.save(airHockeyPaddles);

            Equipment airHockeyBalls = new Equipment("Air Hockey Pucks", "Standard air hockey balls", "url_to_image", 12, airHockeyCategory);
            equipmentRepository.save(airHockeyBalls);
        }
    }
}
