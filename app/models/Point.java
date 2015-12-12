package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Created by yellowstar on 12/4/15.
 */
public class Point {
    private double lat;
    private double lot;

    public Point() {

    }

    public Point(String val) {
        String[] cdt = val.substring(1, val.length() - 1).split(",");
        this.lat = Double.valueOf(cdt[0]);
        this.lot = Double.valueOf(cdt[1]);
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLot() {
        return lot;
    }

    public void setLot(double lot) {
        this.lot = lot;
    }

    @JsonIgnore
    public String getValue() {
        return "(" + this.lat + "," + this.lot + ")";
    }
}
