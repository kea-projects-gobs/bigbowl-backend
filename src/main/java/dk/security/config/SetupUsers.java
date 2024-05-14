package dk.security.config;

import dk.security.entity.Customer;
import dk.security.entity.Employee;
import dk.security.entity.Role;
import dk.security.repository.CustomerRepository;
import dk.security.repository.EmployeeRepository;
import dk.security.repository.RoleRepository;
import dk.security.repository.UserWithRolesRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Component
public class SetupUsers implements ApplicationRunner {

    UserWithRolesRepository userWithRolesRepository;
    RoleRepository roleRepository;
    EmployeeRepository employeeRepository;
    CustomerRepository customerRepository;
    PasswordEncoder pwEncoder;

    public SetupUsers(UserWithRolesRepository userWithRolesRepository, RoleRepository roleRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository) {
        this.userWithRolesRepository = userWithRolesRepository;
        this.roleRepository = roleRepository;
        this.customerRepository = customerRepository;
        this.pwEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        setupAllowedRoles();
        setUpCustomers();
        setUpEmployees();
    }

    private void setupAllowedRoles() {
        roleRepository.save(new Role("CUSTOMER"));
        roleRepository.save(new Role("EMPLOYEE"));
    }

    private void setUpCustomers() {
        Role roleCustomer = roleRepository.findById("CUSTOMER").orElseThrow(() -> new NoSuchElementException("Role 'customer' not found"));
        Customer testcustomer = new Customer("Johnersej123", pwEncoder.encode("secret"), "john@mail.com", "John Doe", "Pilegårdsvej 22");

        testcustomer.addRole(roleCustomer);
        customerRepository.save(testcustomer);
    }

    private void setUpEmployees(){
        Role roleEmployee = roleRepository.findById("EMPLOYEE").orElseThrow(() -> new NoSuchElementException("Role 'employee' not found"));
        Employee testemployee = new Employee("peterpedal", pwEncoder.encode("secret"), "peterlind@mail.com", "Peter Lind", "Pilegårdsvej 22", LocalDate.parse("2020-01-01"), null, 150);

        testemployee.addRole(roleEmployee);
        employeeRepository.save(testemployee);
    }


}
