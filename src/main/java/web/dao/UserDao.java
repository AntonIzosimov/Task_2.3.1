package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    void saveUser(User user);

    void removeUser(Long id);

    User editUser(User user, Long id);

    User getUserById(Long id);
}
