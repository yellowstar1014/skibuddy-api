package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yellowstar on 12/4/15.
 */
public class Path {
    private List<Point> points;
    public Path() {

    }
    public Path(String val) {
        points = new LinkedList<>();
        val = val.substring(1, val.length() - 1);
        int l = 1;
        for (int i = 0; i < val.length(); i++) {
            if (val.charAt(i) == '(') {
                l = i;
            } else if (val.charAt(i) == ')') {
                Point point = new Point(val.substring(l, i + 1));
                points.add(point);
            }
        }
    }

    @JsonIgnore
    public String getValue() {
        String val = "(";
        for (Point point : points) {
            val += point.getValue();
            val += ",";
        }
        val = val.substring(0, val.length() - 1);
        val += ")";
        return val;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}
