package dk.bigbowl.staff.config;

import dk.bigbowl.staff.entity.Shift;
import dk.bigbowl.staff.repository.ShiftRepository;
import dk.security.entity.UserWithRoles;
import dk.security.repository.UserWithRolesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Component
@Order(2)
public class InitShiftData implements CommandLineRunner {


    private final ShiftRepository shiftRepository;
    private final UserWithRolesRepository userWithRolesRepository;

    public InitShiftData(ShiftRepository shiftRepository, UserWithRolesRepository userWithRolesRepository) {
        this.shiftRepository = shiftRepository;
        this.userWithRolesRepository = userWithRolesRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createShifts();
    }

    private void createShifts() {
        List<UserWithRoles> employees = userWithRolesRepository.findAll();

        // Filter employees with the EMPLOYEE role
        List<UserWithRoles> employeeList = employees.stream()
                .filter(employee -> employee.getRoles().stream()
                        .anyMatch(role -> role.getRoleName().equals("SALE")))
                .collect(Collectors.toList());

        // Ensure there are enough employees
        if (employeeList.size() < 4) {
            throw new RuntimeException("Not enough employees to create shifts");
        }

        // Create shifts for the next 10 days
        for (int day = 0; day < 10; day++) {
            LocalDate date = LocalDate.now().plusDays(day);

            // Shuffle the employee list to get random employees each day
            Collections.shuffle(employeeList);
            
            // Assign 2 employees for the morning shift (10:00 to 14:30)
            for (int i = 0; i < 2; i++) {
                UserWithRoles employee = employeeList.get(i);
                LocalTime startTime = LocalTime.of(10, 0); // 10:00
                LocalTime endTime = startTime.plusHours(4).plusMinutes(30); // 4.5-hour shift

                Shift shift = new Shift();
                shift.setDate(date);
                shift.setStartTime(startTime);
                shift.setEndTime(endTime);
                shift.setEmployee(employee);

                shiftRepository.save(shift);
            }

            // Assign 2 employees for the evening shift (14:30 to 19:00)
            for (int i = 2; i < 4; i++) {
                UserWithRoles employee = employeeList.get(i);
                LocalTime startTime = LocalTime.of(14, 30); // 14:30
                LocalTime endTime = startTime.plusHours(4).plusMinutes(30); // 4.5-hour shift

                Shift shift = new Shift();
                shift.setDate(date);
                shift.setStartTime(startTime);
                shift.setEndTime(endTime);
                shift.setEmployee(employee);

                shiftRepository.save(shift);
            }
        }
    }
}
