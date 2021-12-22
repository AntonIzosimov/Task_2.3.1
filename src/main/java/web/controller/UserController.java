package web.controller;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/admin")
    public String getAll(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/admin/user-create")
    public String createUserForm(Model model) {
        List<Role> roleList = roleService.getRoles();
        model.addAttribute("user", new User());
        model.addAttribute("roleList", roleList);
        return "user-create";
    }

    @PostMapping("/admin/user-create")
    public String createUser(@ModelAttribute("user") User user, @RequestParam("roles") String[] roles) {
        String pass = user.getPassword();
        user.setRoles(addRolesToUser(roles));
        user.setPassword(new BCryptPasswordEncoder().encode(pass));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("user-delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user-edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        List<Role> roleList = roleService.getRoles();
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleList);
        return "/user-edit";
    }
    @PatchMapping("/admin/user-edit/{id}")
    public String edit(@ModelAttribute("user") User user, @RequestParam("roles") String[] roles) {
        String pass = user.getPassword();
        user.setPassword(new BCryptPasswordEncoder().encode(pass));
        user.setRoles(addRolesToUser(roles));
        userService.editUser(user);
        return "redirect:/admin";
    }

    private Set<Role> addRolesToUser(String[] roles) {
        List<Role> roleList = roleService.getRoles();
        Set<Role> userRoleSet = new HashSet<>();

        for (Role role : roleList) {
            for (int i = 0; i < roles.length; i++) {
                if(roles[i].contains(role.getName())){
                    userRoleSet.add(role);
                }
            }
        }
        return userRoleSet;
    }

    @RequestMapping("/user")
    public String profile(Principal principal, Model model) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "/user";
    }
}
