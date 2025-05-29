package models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import managedBeaans.AreaCheck;
import mbeans.AreaSquare;
import mbeans.MBeanRegistry;
import mbeans.PointMonitor;

import java.io.Serializable;


@Entity
public class Point implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double x;
    private double y;
    private double r;
    private boolean isHit;

    public boolean getisHit() {
        return isHit;
    }

    public void setisHit(boolean status) {
        this.isHit = status;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Point(){}
    public Point(double x, double y, double r, boolean isHit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isHit = isHit;
    }
}