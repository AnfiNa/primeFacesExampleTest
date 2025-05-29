package mbeans;


public class AreaSquare implements AreaSquareMBean{

    private double areaSquare = 0;

    @Override
    public double getAreaSquare() {
        return areaSquare;
    }

    @Override
    public void makeAreaSquare(double areaSquare) {
        this.areaSquare = areaSquare;
    }

}