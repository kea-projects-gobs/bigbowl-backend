package dk.security.config;

import dk.security.entity.Role;
import dk.security.entity.UserWithRoles;
import dk.security.repository.RoleRepository;
import dk.security.repository.UserWithRolesRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.NoSuchElementException;

@Component
@Order(1)
public class SetupUsers implements ApplicationRunner {

    UserWithRolesRepository userWithRolesRepository;
    RoleRepository roleRepository;
    PasswordEncoder pwEncoder;

    public SetupUsers(UserWithRolesRepository userWithRolesRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userWithRolesRepository = userWithRolesRepository;
        this.roleRepository = roleRepository;
        this.pwEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        setupAllowedRoles();
        setUpCustomers();
        setUpEmployees();
        setupAdmin();
    }

    private void setupAllowedRoles() {
        roleRepository.save(new Role("CUSTOMER"));
        roleRepository.save(new Role("EMPLOYEE"));
        roleRepository.save(new Role("RESERVATION_SALE"));
        roleRepository.save(new Role("OPERATOR"));
        roleRepository.save(new Role("ADMIN"));
    }

    private void setUpCustomers() {
        Role roleCustomer = roleRepository.findById("CUSTOMER").orElseThrow(() -> new NoSuchElementException("Role 'customer' not found"));
        UserWithRoles testcustomer = new UserWithRoles("John123", pwEncoder.encode("secret"), "john@mail.com", "John Doe", "Random");

        testcustomer.addRole(roleCustomer);
        userWithRolesRepository.save(testcustomer);
    }

    private void setUpEmployees() {
        Role roleEmployee = roleRepository.findById("EMPLOYEE").orElseThrow(() -> new NoSuchElementException("Role 'employee' not found"));
        Role roleReservationSale = roleRepository.findById("RESERVATION_SALE").orElseThrow(() -> new NoSuchElementException("Role 'reservation_sale' not found"));
        Role roleOperator = roleRepository.findById("OPERATOR").orElseThrow(() -> new NoSuchElementException("Role 'operator' not found"));

        // 12 employees in different roles schedules
        for (int i = 1; i <= 12; i++) {
            String username = "employee" + i;
            String email = "employee" + i + "@mail.com";
            String name = "Employee " + i;
            String address = "Employee Address " + i;

            UserWithRoles employee = new UserWithRoles(username, pwEncoder.encode("secret"), email, name, address);
            employee.addRole(roleEmployee);
            userWithRolesRepository.save(employee);
        }

        // 2 employees to handle reservations and sales
        for (int i = 1; i <= 2; i++) {
            String username = "reservationSale" + i;
            String email = "reservationSale" + i + "@mail.com";
            String name = "Reservation Sale " + i;
            String address = "Reservation Sale Address " + i;

            UserWithRoles reservationSale = new UserWithRoles(username, pwEncoder.encode("secret"), email, name, address);
            reservationSale.addRole(roleReservationSale);
            userWithRolesRepository.save(reservationSale);
        }

        // 1 operator
        UserWithRoles operator = new UserWithRoles("operator", pwEncoder.encode("secret"), "operator@mail.com", "Operator", "Operator Address");
        operator.addRole(roleOperator);
        userWithRolesRepository.save(operator);
    }

    private void setupAdmin() {
        Role roleAdmin = roleRepository.findById("ADMIN").orElseThrow(() -> new NoSuchElementException("Role 'admin' not found"));
        UserWithRoles testadmin = new UserWithRoles("admin", pwEncoder.encode("secret"), "admin@mail.com", "Admin", "Pilegårdsvej 22");

        testadmin.addRole(roleAdmin);
        userWithRolesRepository.save(testadmin);
    }
}
