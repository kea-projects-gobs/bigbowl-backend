package dk.bigbowl.staff.service;

import dk.bigbowl.staff.dto.ShiftDTO;
import dk.bigbowl.staff.entity.Shift;

import java.util.List;

public interface ShiftService {

    List<ShiftDTO> getAllShifts();
    ShiftDTO createShift(ShiftDTO shiftDTO);
    ShiftDTO updateShift(Long id, ShiftDTO shiftDTO);
    void deleteShift(Long id);
    ShiftDTO convertToDTO(Shift shift);
    Shift convertToEntity(ShiftDTO shiftDTO);
}
