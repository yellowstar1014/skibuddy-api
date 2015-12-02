package models;

import org.postgresql.geometric.PGpath;

import java.util.Date;

/**
 * Created by yellowstar on 11/29/15.
 */
public class RecordDTO {
    private Date startTime;
    private Date endTime;
    private double distance;
    private PGpath path;

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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public PGpath getPath() {
        return path;
    }

    public void setPath(PGpath path) {
        this.path = path;
    }
}
