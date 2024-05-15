package dk.security.config;

import dk.security.entity.Role;
import dk.security.entity.UserWithRoles;
import dk.security.repository.RoleRepository;
import dk.security.repository.UserWithRolesRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.NoSuchElementException;

@Component
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
        roleRepository.save(new Role("ADMIN"));
    }

    private void setUpCustomers() {
        Role roleCustomer = roleRepository.findById("CUSTOMER").orElseThrow(() -> new NoSuchElementException("Role 'customer' not found"));
        UserWithRoles testcustomer = new UserWithRoles("Johnersej123", pwEncoder.encode("secret"), "john@mail.com", "John Doe", "Pilegårdsvej 22");

        testcustomer.addRole(roleCustomer);
        userWithRolesRepository.save(testcustomer);
    }

    private void setUpEmployees(){
        Role roleEmployee = roleRepository.findById("EMPLOYEE").orElseThrow(() -> new NoSuchElementException("Role 'employee' not found"));
        UserWithRoles testemployee = new UserWithRoles("peterpedal", pwEncoder.encode("secret"), "peterlind@mail.com", "Peter Lind", "Pilegårdsvej 22");

        testemployee.addRole(roleEmployee);
        userWithRolesRepository.save(testemployee);
    }

    private void setupAdmin(){
        Role roleAdmin = roleRepository.findById("ADMIN").orElseThrow(() -> new NoSuchElementException("Role 'admin' not found"));
        UserWithRoles testadmin = new UserWithRoles("admin", pwEncoder.encode("secret"), "admin@mail.com", "Admin", "Pilegårdsvej 22");

        testadmin.addRole(roleAdmin);
        userWithRolesRepository.save(testadmin);
    }


}
