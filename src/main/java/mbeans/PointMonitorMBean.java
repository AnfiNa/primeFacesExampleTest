package mbeans;

import models.Point;

public interface PointMonitorMBean {
    int getCountAllPoints();
    int getCountWrongPoints();
    void notifyIfPointOutsideArea(double x, double y, double radius);
}
