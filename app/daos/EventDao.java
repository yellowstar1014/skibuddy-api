package daos;

import models.Event;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Created by yellowstar on 11/23/15.
 */
public class EventDao {
    @Transactional
    public Event create(Event event) {
        EntityManager em = JPA.em();
        em.persist(event);
        return event;
    }

    @Transactional
    public Event findById(int eid) {
        EntityManager em = JPA.em();
        return em.find(Event.class, eid);
    }
}
