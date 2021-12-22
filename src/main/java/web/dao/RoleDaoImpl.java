package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Transactional
@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getRoles() {
        return entityManager.createQuery("from Role", Role.class)
                .getResultList();
    }

    @Override
    public void saveRole(Role roles) {
        entityManager.persist(roles);
    }
}
