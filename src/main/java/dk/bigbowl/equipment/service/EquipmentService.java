package dk.bigbowl.equipment.service;

import dk.bigbowl.equipment.entity.Equipment;

import java.util.List;

public interface EquipmentService {
    String checkAndOrderEquipment();

    List<Equipment> getAllEquipment();

    Equipment getEquipmentById(Long id);
}
