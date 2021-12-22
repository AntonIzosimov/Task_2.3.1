package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    void saveUser(User user);

    void removeUser(Long id);

    void editUser(User user);

    User getUserById(Long id);

    User getUserByName(String username);
}
