package dk.bigbowl.staff.api;

import dk.bigbowl.staff.dto.ShiftDTO;
import dk.bigbowl.staff.service.ShiftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    private final ShiftService shiftService;


    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @GetMapping
    public ResponseEntity<List<ShiftDTO>> getAllShifts(){
        return ResponseEntity.ok().body(shiftService.getAllShifts());
    }

    @PostMapping
    public ResponseEntity<ShiftDTO> createShift(@RequestBody ShiftDTO shiftDTO){
        return ResponseEntity.ok().body(shiftService.createShift(shiftDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShiftDTO> updateShift(@PathVariable Long id, @RequestBody ShiftDTO shiftDTO){
        return ResponseEntity.ok().body(shiftService.updateShift(id, shiftDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ShiftDTO> deleteShift(@PathVariable Long id){
        shiftService.deleteShift(id);
        return ResponseEntity.noContent().build();
    }
}
