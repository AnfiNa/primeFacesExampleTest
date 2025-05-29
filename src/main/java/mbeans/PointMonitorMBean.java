package mbeans;

import models.Point;

public interface PointMonitorMBean {
    int getCountAllPoints();
    int getCountWrongPoints();
    void notifyIfPointOutsideArea(Point point);
}
