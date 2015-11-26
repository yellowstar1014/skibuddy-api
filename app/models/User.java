package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.postgresql.geometric.PGpoint;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yellowstar on 11/15/15.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "email")
    private String email;
    @Column(name = "expiration")
    private Date expiration;
    @Column(name = "google_id")
    private String googleId;
    @OneToMany(mappedBy = "owner")
    private List<Record> records;
    @OneToMany (mappedBy = "user")
    private List<Attend> attends;

    @JsonIgnore
    public List<Attend> getAttends() {
        return attends;
    }

    public void setAttends(List<Attend> attends) {
        this.attends = attends;
    }

    public List<UserEventWithStatus> getUserEvent() {
        List<UserEventWithStatus> list = new LinkedList<>();
        for (Attend attend : attends) {
            UserEventWithStatus ue = new UserEventWithStatus();
            ue.setEvent(attend.getEvent());
            ue.setStatus(attend.getStatus());
            list.add(ue);
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String google_id) {
        this.googleId = google_id;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
