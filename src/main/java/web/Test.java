package web;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;


import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Test {

    UserService userService;
    RoleService roleService;

    public Test(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void Runner() {

        Set<Role> adminSet = new HashSet<>();
        Set<Role> userSet = new HashSet<>();

        Role role = new Role("ADMIN");
        Role role2 = new Role("USER");

        adminSet.add(role);
        adminSet.add(role2);
        userSet.add(role2);

        roleService.saveRole(role);
        roleService.saveRole(role2);

        User user = new User();
        user.setUsername("Nadya");
        user.setLastName("Evseeva");
        user.setAge((byte) 27);
        user.setPassword(new BCryptPasswordEncoder().encode("100"));
        user.setRoles(adminSet);

        User user2 = new User();
        user2.setUsername("Nastya");
        user2.setLastName("Korneeva");
        user2.setAge((byte) 25);
        user2.setPassword(new BCryptPasswordEncoder().encode("100"));
        user2.setRoles(userSet);

        userService.saveUser(user);
        userService.saveUser(user2);
    }
}
