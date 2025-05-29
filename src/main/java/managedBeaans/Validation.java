package managedBeaans;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import mbeans.AreaSquare;
import mbeans.PointMonitor;
import models.Point;

import java.io.Serializable;

@Named("validation")
@ApplicationScoped
public class Validation implements Serializable {
    private final AreaSquare areaSquare = new AreaSquare();
    @Inject
    AreaCheck areaCheck;
    public boolean validate(double x, double y, double r) {
        areaSquare.setAreaSquare(8);
        return (validateX(x) && validateY(y) && validateR(r));
    }

    private boolean validateX(double x) {
        return x >= -5 && x <= 3;
    }

    private boolean validateY(double y) {
        return y >= -4 && y <= 4;
    }

    private boolean validateR(double r) {
        return r >= 1 && r <= 3;
    }

}