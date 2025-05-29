package mbeans;

import models.Point;

import java.util.concurrent.atomic.AtomicInteger;

public class AreaSquare implements AreaSquareMBean{

    private final double areaSquare = new AtomicInteger();

    @Override
    public double getAreaSquare() {
        return areaSquare;
    }

    @Override
    public void setAreaSquare(double areaSquare) {
        this.areaSquare = areaSquare;
    }

}