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

        return checkAndOrderSpecificEquipment("Bowling Kegler", MINIMUM_BOWLING_PINS) +
                checkAndOrderSpecificEquipment("Bowling Sko", MINIMUM_BOWLING_SHOES);
    }

    @Override
    public List<Equipment> getAllEquipment() {
        List<Equipment> equipmentList = equipmentRepository.findAll();
        int numberOfLanes = activityRepository.countByTypeName("Bowling");
        int numberOfAirHockeyTables = activityRepository.countByTypeName("Air hockey");

        for (Equipment equipment : equipmentList) {
            int minimumStock = switch (equipment.getName()) {
                case "Bowling Kegler" -> numberOfLanes * 9;
                case "Bowling Sko" -> numberOfLanes * 6;
                case "Bowling Kugler" -> numberOfLanes * 12;
                case "Air Hockey Håndtag" -> numberOfAirHockeyTables * 2;
                case "Air Hockey Pucks" -> numberOfAirHockeyTables * 12;
                default -> 0;
            };

            if (minimumStock > 0 && equipment.getStock() < minimumStock) {
                equipment.setRequiredAmount(minimumStock - equipment.getStock());
            } else {
                equipment.setRequiredAmount(0);
            }
        }

        return equipmentList;
    }

    @Override
    public Equipment getEquipmentById(Long id) {
        return equipmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipment not found"));
    }

    @Override
    public String orderSpecificEquipment(String equipmentName) {
        int numberOfLanes = activityRepository.countByTypeName("Bowling");
        int numberOfAirHockeyTables = activityRepository.countByTypeName("Air hockey");
        int minimumStock;

        switch (equipmentName) {
            case "Bowling Kegler":
                minimumStock = numberOfLanes * 9;
                break;
            case "Bowling Sko":
                minimumStock = numberOfLanes * 6;
                break;
            case "Bowling Kugler":
                minimumStock = numberOfLanes * 12;
                break;
            case "Air hockey håndtag":
                minimumStock = numberOfAirHockeyTables * 2;
                break;
            case "Air hockey pucks":
                minimumStock = numberOfAirHockeyTables * 4; 
                break;
            default:
                throw new IllegalArgumentException("Unknown equipment: " + equipmentName);
        }

        return checkAndOrderSpecificEquipment(equipmentName, minimumStock);
    }

    private String checkAndOrderSpecificEquipment(String equipmentName, int minimumStock) {
        Equipment equipment = equipmentRepository.findByName(equipmentName);
        if (equipment != null && equipment.getStock() < minimumStock) {
            int needed = minimumStock - equipment.getStock();
            equipment.setStock(equipment.getStock() + needed);
            equipmentRepository.save(equipment);
            return "Bestilt " + needed + " nye " + equipment.getName() + ".\n";
        }
        return "Der er nok " + equipmentName + " på lager";
    }
}
