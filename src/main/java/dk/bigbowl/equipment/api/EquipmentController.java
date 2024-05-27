
package dk.bigbowl.equipment.api;

import dk.bigbowl.equipment.entity.Equipment;
import dk.bigbowl.equipment.service.EquipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        List<Equipment> equipmentList = equipmentService.getAllEquipment();
        return ResponseEntity.ok().body(equipmentList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable Long id) {
        return ResponseEntity.ok().body(equipmentService.getEquipmentById(id));
    }

    @PostMapping("/order")
    public ResponseEntity<String> checkAndOrderEquipment(@RequestParam String equipmentName) {
        String result = equipmentService.orderSpecificEquipment(equipmentName);
        return ResponseEntity.ok(result);
    }
}
