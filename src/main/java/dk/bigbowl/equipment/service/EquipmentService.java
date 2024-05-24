package dk.bigbowl.equipment.service;

import dk.bigbowl.equipment.entity.Equipment;

import java.util.List;

public interface EquipmentService {
    List<Equipment> getAllEquipment();

    Equipment getEquipmentById(Long id);

    String orderSpecificEquipment(String equipmentName);
}
