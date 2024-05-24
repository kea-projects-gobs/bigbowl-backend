package dk.bigbowl.equipment.service.impl;

import dk.bigbowl.equipment.entity.Equipment;
import dk.bigbowl.equipment.repository.EquipmentRepository;
import dk.bigbowl.equipment.service.EquipmentService;
import dk.bigbowl.reservation.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final ActivityRepository activityRepository;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, ActivityRepository activityRepository) {
        this.equipmentRepository = equipmentRepository;
        this.activityRepository = activityRepository;
    }

    public String checkAndOrderEquipment() {
        int numberOfLanes = activityRepository.countByTypeName("Bowling");
        int MINIMUM_BOWLING_PINS = numberOfLanes * 9;
        int MINIMUM_BOWLING_SHOES = numberOfLanes * 6;

        return checkAndOrderSpecificEquipment("Bowling Pins", MINIMUM_BOWLING_PINS) +
                checkAndOrderSpecificEquipment("Bowling Shoes", MINIMUM_BOWLING_SHOES);
    }

    @Override
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    @Override
    public Equipment getEquipmentById(Long id) {
        return equipmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipment not found"));
    }

    private String checkAndOrderSpecificEquipment(String equipmentName, int minimumStock) {
        Equipment equipment = equipmentRepository.findByName(equipmentName);
        if (equipment != null && equipment.getStock() < minimumStock) {
            int needed = minimumStock - equipment.getStock();
            equipment.setStock(equipment.getStock() + needed);
            equipmentRepository.save(equipment);
            return "Ordered " + needed + " replacements for " + equipment.getName() + ".\n";
        }
        return "No replacements needed for " + equipmentName + ".\n";
    }
}
