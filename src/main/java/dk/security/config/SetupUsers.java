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
        roleRepository.save(new Role("SALE"));
        roleRepository.save(new Role("OPERATOR"));
        roleRepository.save(new Role("MANAGER"));
    }

    private void setUpCustomers() {
        Role roleCustomer = roleRepository.findById("CUSTOMER").orElseThrow(() -> new NoSuchElementException("Role 'customer' not found"));
        UserWithRoles testcustomer = new UserWithRoles("John123", pwEncoder.encode("secret"), "john@mail.com", "John Doe", "Random");

        testcustomer.addRole(roleCustomer);
        userWithRolesRepository.save(testcustomer);
    }

    private void setUpEmployees() {
        Role roleSale = roleRepository.findById("SALE").orElseThrow(() -> new NoSuchElementException("Role 'reservation_sale' not found"));
        Role roleOperator = roleRepository.findById("OPERATOR").orElseThrow(() -> new NoSuchElementException("Role 'operator' not found"));


        // 12 employees to handle reservations and sales
        for (int i = 1; i <= 12; i++) {
            String username = "Sale" + i;
            String email = "Sale" + i + "@mail.com";
            String name = "Sale " + i;
            String address = "Sale Address " + i;

            UserWithRoles Sale = new UserWithRoles(username, pwEncoder.encode("secret"), email, name, address);
            Sale.addRole(roleSale);
            userWithRolesRepository.save(Sale);
        }

        // 1 operator
        UserWithRoles operator = new UserWithRoles("operator", pwEncoder.encode("secret"), "operator@mail.com", "Operator", "Operator Address");
        operator.addRole(roleOperator);
        userWithRolesRepository.save(operator);
    }

    private void setupAdmin() {
        Role roleManager = roleRepository.findById("MANAGER").orElseThrow(() -> new NoSuchElementException("Role 'manager' not found"));
        UserWithRoles manager  = new UserWithRoles("manager", pwEncoder.encode("secret"), "manager@mail.com", "Manager", "Manager Address");

        manager.addRole(roleManager);
        userWithRolesRepository.save(manager);
    }
}
