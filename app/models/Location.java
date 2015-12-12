package models;

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
    private String curLocation;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Point getCurLocation() {
        return new Point(curLocation);
    }

    public void setCurLocation(Point curLocation) {
        this.curLocation = curLocation.getValue();
    }
}
