package web.service;

import web.model.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void saveUser(User user);

    void removeUser(Long id);

    void editUser(User user);

    User getUserById(Long id);

    User getUserByName(String username);
}
