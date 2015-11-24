package daos;

import models.User;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by yellowstar on 11/23/15.
 */
public class UserDao {
    @Transactional
    public User create(User user) {
        EntityManager em = JPA.em();
        em.persist(user);
        return user;
    }

    @Transactional
    public User findById(int uid) {
        return JPA.em().find(User.class, uid);
    }

    @Transactional
    public User findBySub(String sub) {
        Query query  = JPA.em().createQuery("select u from User u where u.google_id = " + ":sub");
        query.setParameter("sub", sub);
        List<User> users = query.getResultList();
        if (users.size() == 0) return null;
        return users.get(0);
    }
}
