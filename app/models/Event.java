package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yellowstar on 11/15/15.
 */
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "s_time")
    private Date startTime;
    @Column(name = "e_time")
    private Date endTime;
    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;
    @OneToMany(mappedBy = "event")
    private List<Attend> attends;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @JsonIgnore
    public List<Attend> getAttends() {
        return attends;
    }

    public void addAttend(Attend attend) {
        if (attends == null) {
            attends = new LinkedList<>();
        }
        attends.add(attend);
    }


    public void setAttends(List<Attend> attends) {
        this.attends = attends;
    }

    public List<EventUserWithStatus> getEventUser() {
        List<EventUserWithStatus> list = new LinkedList<>();
        if (attends == null) return list;
        for (Attend attend : attends) {
            EventUserWithStatus eu = new EventUserWithStatus();
            eu.setStatus(attend.getStatus());
            eu.setUser(attend.getUser());
        }
        return list;
    }
}
