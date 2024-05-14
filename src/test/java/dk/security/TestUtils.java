package dk.security;

import dk.security.entity.Role;
import dk.security.entity.UserWithRoles;
import dk.security.repository.RoleRepository;
import dk.security.repository.UserWithRolesRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestUtils {


  public static void setupTestUsers(UserWithRolesRepository userWithRolesRepository, RoleRepository roleRes, PasswordEncoder pwEn){
    userWithRolesRepository.deleteAll();
    String pw = "secret";
    Role customerRole = new Role("CUSTOMER");
    Role employeeRole = new Role("EMPLOYEE");
    roleRes.save(customerRole);
    roleRes.save(employeeRole);
    UserWithRoles user1 = new UserWithRoles("u1", pwEn.encode(pw), "u1@a.dk");
    UserWithRoles user2 = new UserWithRoles("u2", pwEn.encode(pw), "u2@a.dk");
    UserWithRoles user3 = new UserWithRoles("u3", pwEn.encode(pw), "u3@a.dk");
    UserWithRoles userNoRoles = new UserWithRoles("u4", pwEn.encode(pw), "u4@a.dk");
    user1.addRole(customerRole);
    user1.addRole(employeeRole);
    user2.addRole(customerRole);
    user3.addRole(employeeRole);
    userWithRolesRepository.save(user1);
    userWithRolesRepository.save(user2);
    userWithRolesRepository.save(user3);
    userWithRolesRepository.save(userNoRoles);
  }
}
