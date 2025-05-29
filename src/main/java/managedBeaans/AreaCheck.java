package managedBeaans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import models.Point;

import java.io.Serializable;

@Named("areaCheck")
@ApplicationScoped
public class AreaCheck implements Serializable {

    public boolean isInArea(double x, double y, double r){
        return checkSquare(x, y, r) || checkCircle(x, y, r) || checkTriangle(x, y, r);
    }

    public static double calculateArea(Point point) {
        double r = point.getR();
        return r*r*(1+3.14/4);
    }

    private boolean checkSquare(double x, double y, double r){
        return (y>=0 && y<=r/2 && x >= 0 && x <= r);
    }

    private boolean checkCircle(double x, double y, double r){
        return (x <= 0 && y <= 0 && Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) <= r/2);
    }

    private boolean checkTriangle(double x, double y, double r){
        boolean withinXBounds = (0 <= x);
        boolean withinYBounds = (y <= 0);
        boolean aboveHypotenuse = (y >= x - r);

        return withinXBounds && withinYBounds && aboveHypotenuse;
    }
}
