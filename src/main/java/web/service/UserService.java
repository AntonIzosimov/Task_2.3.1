package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void saveUser(User user);

    void removeUser(Long id);

    User editUser(User user, Long id);

    User getUserById(Long id);
}
