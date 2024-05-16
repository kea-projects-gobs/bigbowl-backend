package dk.security.dto;


import dk.security.entity.UserWithRoles;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserWithRolesResponse {
    String userName;
    List<String> roleNames;
    String email;
    String name;
    String address;

    public UserWithRolesResponse(UserWithRoles userWithRoles){
        this.userName = userWithRoles.getUsername();
        this.roleNames = userWithRoles.getRoles().stream().map(role -> role.getRoleName()).toList();
        this.email = userWithRoles.getEmail();
        this.name = userWithRoles.getName();
        this.address = userWithRoles.getAddress();
    }

}
