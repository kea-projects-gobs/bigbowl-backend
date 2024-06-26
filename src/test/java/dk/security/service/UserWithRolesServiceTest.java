package dk.security.service;

import dk.security.TestUtils;
import dk.security.dto.UserWithRolesRequest;
import dk.security.dto.UserWithRolesResponse;
import dk.security.entity.UserWithRoles;
import dk.security.repository.RoleRepository;
import dk.security.repository.UserWithRolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

//You can enable/disable these tests in you maven builds via the maven-surefire-plugin, in your pom-file
//@Tag("DisabledSecurityTest")
@DataJpaTest
class UserWithRolesServiceTest {

  //@Autowired
  UserWithRolesService userWithRolesService;

  @Autowired
  UserWithRolesRepository userWithRolesRepository;

  @Autowired
  RoleRepository roleRepository;

  //@Autowired
  @MockBean
  PasswordEncoder passwordEncoder;

  private boolean dataInitialized = false;

  @BeforeEach
  void setUp() {
    Mockito.when(passwordEncoder.encode("secret")).thenReturn("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

    //userWithRolesService.setDefaultRoleName("USER"); //Could also be done in the TEST application.properties
    if(!dataInitialized) {

      userWithRolesService = new UserWithRolesService(userWithRolesRepository,roleRepository,passwordEncoder);
      userWithRolesRepository.deleteAll();
      TestUtils.setupTestUsers(userWithRolesRepository,roleRepository,passwordEncoder);
      dataInitialized = true;
    }
  }


  @Test
  void getUserWithRoles() {
    UserWithRolesResponse user = userWithRolesService.getUserWithRoles("u1");
    assertEquals(3, user.getRoleNames().size());
    assertTrue(user.getRoleNames().contains("CUSTOMER"));
    assertTrue(user.getRoleNames().contains("EMPLOYEE"));
    assertTrue(user.getRoleNames().contains("ADMIN"));
  }

  @Test
  void addRole() {
    UserWithRolesResponse user = userWithRolesService.addRole("u4", "CUSTOMER");
    assertEquals(1, user.getRoleNames().size());
    assertTrue(user.getRoleNames().contains("CUSTOMER"));
  }

  @Test
  void removeRole() {
    UserWithRolesResponse user = userWithRolesService.removeRole("u1", "EMPLOYEE");
    assertEquals(2, user.getRoleNames().size());
    assertFalse(user.getRoleNames().contains("EMPLOYEE"));
    user = userWithRolesService.removeRole("u1", "EMPLOYEE");
    assertEquals(2, user.getRoleNames().size());
  }

  @Test
  void editUserWithRoles() {
    Mockito.when(passwordEncoder.encode("new_Password")).thenReturn("aaaxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    String originalPassword = userWithRolesRepository.findById("u1").get().getPassword();
    UserWithRolesRequest user1 = new UserWithRolesRequest("u1New", "new_Password", "newMail@a.dk", "newName", "newAddress");
    UserWithRolesResponse user = userWithRolesService.editUserWithRoles("u1",user1);
    assertEquals("u1", user.getUserName());  //IMPORTANT: The username should not be changed
    assertEquals("newMail@a.dk", user.getEmail());
    UserWithRoles editedUser = userWithRolesRepository.findById("u1").get();
    assertNotEquals(originalPassword, editedUser.getPassword());
  }

  @Test
  void addUserWithRolesWithNoRole() {
    Mockito.when(passwordEncoder.encode("new_Password")).thenReturn("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    userWithRolesService.setDefaultRoleName(null);
    UserWithRolesRequest user = new UserWithRolesRequest("u5", "new_Password", "xx@x.dk", "newName", "newAddress");
    UserWithRolesResponse newUser = userWithRolesService.addUserWithRoles(user);
    assertEquals(0, newUser.getRoleNames().size());
    assertEquals("u5", newUser.getUserName());
    assertEquals("xx@x.dk", newUser.getEmail());
    //Verify that the password is hashed
    UserWithRoles userFromDB = userWithRolesRepository.findById("u5").get();
    assertEquals(60,userWithRolesRepository.findById("u5").get().getPassword().length());
  }
  @Test
  void addUserWithRolesWithRole() {
    Mockito.when(passwordEncoder.encode("new_Password")).thenReturn("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    userWithRolesService.setDefaultRoleName("CUSTOMER");
    UserWithRolesRequest user = new UserWithRolesRequest("u5", "new_Password", "xx@x.dk", "newName", "newAddress");
    UserWithRolesResponse newUser = userWithRolesService.addUserWithRoles(user);
    assertEquals(1, newUser.getRoleNames().size());
    assertTrue(newUser.getRoleNames().contains("CUSTOMER"));
    assertEquals("u5", newUser.getUserName());
    assertEquals("xx@x.dk", newUser.getEmail());
  }

}