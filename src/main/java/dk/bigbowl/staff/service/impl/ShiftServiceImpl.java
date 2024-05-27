package dk.bigbowl.staff.service.impl;

import dk.bigbowl.staff.dto.ShiftDTO;
import dk.bigbowl.staff.entity.Shift;
import dk.bigbowl.staff.repository.ShiftRepository;
import dk.bigbowl.staff.service.ShiftService;
import dk.security.repository.UserWithRolesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShiftServiceImpl implements ShiftService {

    private final UserWithRolesRepository userWithRolesRepository;
    private final ShiftRepository shiftRepository;

    public ShiftServiceImpl(UserWithRolesRepository userWithRolesRepository, ShiftRepository shiftRepository) {
        this.userWithRolesRepository = userWithRolesRepository;
        this.shiftRepository = shiftRepository;
    }

    @Override
    public List<ShiftDTO> getAllShifts() {
        return shiftRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ShiftDTO createShift(ShiftDTO shiftDTO) {
        Shift shift = convertToEntity(shiftDTO);
        shiftRepository.save(shift);
        return convertToDTO(shift);
    }

    @Override
    public ShiftDTO updateShift(Long id, ShiftDTO shiftDTO) {
        Shift existingShift = shiftRepository.findById(id).orElseThrow(() -> new RuntimeException("Shift not found"));
        existingShift.setDate(shiftDTO.getDate());
        existingShift.setStartTime(shiftDTO.getStartTime());
        existingShift.setEndTime(shiftDTO.getEndTime());
        existingShift.setEmployee(userWithRolesRepository.findByUsername(shiftDTO.getEmployee()).orElseThrow(() -> new RuntimeException("Employee not found")));
        shiftRepository.save(existingShift);

        return convertToDTO(existingShift);
    }

    @Override
    public void deleteShift(Long id) {
        Shift shift = shiftRepository.findById(id).orElseThrow(() -> new RuntimeException("Shift not found"));
        shiftRepository.delete(shift);
    }

    @Override
    public ShiftDTO convertToDTO(Shift shift) {
        ShiftDTO dto = new ShiftDTO();
        dto.setId(shift.getId());
        dto.setDate(shift.getDate());
        dto.setStartTime(shift.getStartTime());
        dto.setEndTime(shift.getEndTime());
        dto.setEmployee(shift.getEmployee().getUsername());
        return dto;
    }

    @Override
    public Shift convertToEntity(ShiftDTO shiftDTO) {
        Shift shift = new Shift();
        shift.setId(shiftDTO.getId());
        shift.setDate(shiftDTO.getDate());
        shift.setStartTime(shiftDTO.getStartTime());
        shift.setEndTime(shiftDTO.getEndTime());
        shift.setEmployee(userWithRolesRepository.findByUsername(shiftDTO.getEmployee()).orElseThrow(() -> new RuntimeException("Employee not found")));
        return shift;
    }
}
