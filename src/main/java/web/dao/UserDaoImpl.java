package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager
                .createQuery("select distinct u from User u left join fetch u.roles", User.class)
                .getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void removeUser(Long id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.createQuery(
                        "from User u join fetch u.roles where u.id =:id", User.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public User getUserByName(String username) {
        return entityManager.createQuery(
                        "from User u join fetch u.roles where u.username =:username", User.class)
                .setParameter("username", username).getSingleResult();
    }

    @Override
    public void editUser(User user) {
        entityManager.merge(user);
    }
}
