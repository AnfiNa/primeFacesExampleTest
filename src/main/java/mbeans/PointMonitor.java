package mbeans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import models.Point;

import javax.management.*;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicInteger;


public class PointMonitor implements PointMonitorMBean, NotificationEmitter {

    // Счетчики точек
    private final AtomicInteger totalPoints = new AtomicInteger(0);
    private final AtomicInteger wrongPoints = new AtomicInteger(0);

    // Для отправки JMX уведомлений
    private final NotificationBroadcasterSupport broadcaster = new NotificationBroadcasterSupport();
    private long sequenceNumber = 1;

    @PostConstruct
    public void registerMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("mbeans:type=PointMonitor");
            if (!mbs.isRegistered(name)) {
                mbs.registerMBean(this, name);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to register MBean", e);
        }
    }

    @PreDestroy
    public void unregisterMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("mbeans:type=PointMonitor");
            if (mbs.isRegistered(name)) {
                mbs.unregisterMBean(name);
            }
        } catch (Exception e) {
            // Логирование ошибки
        }
    }

    @Override
    public int getCountAllPoints() {
        return totalPoints.get();
    }

    @Override
    public int getCountWrongPoints() {
        return wrongPoints.get();
    }

    @Override
    public void notifyIfPointOutsideArea(Point point) {
        totalPoints.incrementAndGet();

        if (!isPointOutsideArea(point)) {
            wrongPoints.incrementAndGet();
            sendNotification(point);
        }
    }

    private boolean isPointOutsideArea(Point point) {
        return point.getisHit();
    }

    private void sendNotification(Point point) {
        Notification notification = new Notification(
                "PointOutsideArea",
                this,
                sequenceNumber++,
                System.currentTimeMillis(),
                String.format("Точка (%.2f, %.2f) вне области!", point.getX(), point.getY())
        );
        broadcaster.sendNotification(notification);
    }

    // Реализация методов NotificationEmitter
    @Override
    public void addNotificationListener(NotificationListener listener,
                                        NotificationFilter filter,
                                        Object handback) {
        broadcaster.addNotificationListener(listener, filter, handback);
    }

    @Override
    public void removeNotificationListener(NotificationListener listener)
            throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(listener);
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

    @Override
    public void removeNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(listener, filter, handback);
    }
}