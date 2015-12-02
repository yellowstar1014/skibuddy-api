package models;

import org.postgresql.geometric.PGpoint;

import javax.persistence.*;

/**
 * Created by yellowstar on 11/25/15.
 */
@Entity
@Table(name = "location")
public class Location {
    @Id
    private int id;
    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;
    @Column(name = "cur_loc")
    private PGpoint curLocation;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PGpoint getCurLocation() {
        return curLocation;
    }

    public void setCurLocation(PGpoint curLocation) {
        this.curLocation = curLocation;
    }
}
