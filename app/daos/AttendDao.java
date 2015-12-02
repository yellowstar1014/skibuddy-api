package daos;

import models.Attend;
import models.Event;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import javax.persistence.EntityManager;

/**
 * Created by yellowstar on 12/2/15.
 */
public class AttendDao {
    @Transactional
    public void create(Attend attend) {
        EntityManager em = JPA.em();
        em.persist(attend);
    }
}
