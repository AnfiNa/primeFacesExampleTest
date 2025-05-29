package mbeans;


import javax.management.*;
import java.util.concurrent.atomic.AtomicInteger;


public class PointMonitor implements PointMonitorMBean, NotificationEmitter {

    // Счетчики точек
    private final AtomicInteger totalPoints = new AtomicInteger(0);
    private final AtomicInteger wrongPoints = new AtomicInteger(0);

    // Для отправки JMX уведомлений
    private final NotificationBroadcasterSupport broadcaster = new NotificationBroadcasterSupport();
    private long sequenceNumber = 1;


    @Override
    public int getCountAllPoints() {
        return totalPoints.get();
    }

    @Override
    public int getCountWrongPoints() {
        return wrongPoints.get();
    }

    @Override
    public void notifyIfPointOutsideArea(double x, double y, double r) {
        totalPoints.incrementAndGet();
        boolean fl = (y>=0 && y<=r/2 && x >= 0 && x <= r) || (x <= 0 && y <= 0 && Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) <= r/2) || (0 <= x) && (y <= 0) && (y >= x - r);
        if (!fl) {
            wrongPoints.incrementAndGet();
            sendNotification(x, y);
        }
    }

    private void sendNotification(double x, double y) {
        Notification n = new Notification(
                "PointOutsideArea", this, sequenceNumber++,
                System.currentTimeMillis(),
                String.format("Точка (%.2f, %.2f) вне области!", x, y)
        );
        broadcaster.sendNotification(n);
    }

    @Override
    public void addNotificationListener(NotificationListener l, NotificationFilter f, Object h) {
        broadcaster.addNotificationListener(l, f, h);
    }

    @Override
    public void removeNotificationListener(NotificationListener l) throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(l);
    }

    @Override
    public void removeNotificationListener(NotificationListener l,
                                           NotificationFilter f,
                                           Object h) throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(l, f, h);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return new MBeanNotificationInfo[]{
                new MBeanNotificationInfo(
                        new String[]{"PointOutsideArea"},
                        Notification.class.getName(),
                        "Уведомление о точке вне области"
                )
        };
    }
}