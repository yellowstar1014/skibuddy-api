package daos;

import models.Record;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Created by yellowstar on 11/23/15.
 */
public class RecordDao {
    @Transactional
    public Record create(Record record) {
        EntityManager em = JPA.em();
        em.persist(record);
        return record;
    }
}
