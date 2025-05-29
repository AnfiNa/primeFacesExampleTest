package mbeans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextListener;
import lombok.experimental.UtilityClass;
import managedBeaans.AreaCheck;
import models.Point;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;


@Singleton
@Startup
public class MBeanRegistry implements ServletContextListener {
    private final Map<Class<?>, ObjectName> beans = new HashMap<>();
    private static final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

    public void registerBean(Object mbean, String name) {
            try {
                ObjectName objName = new ObjectName("com.example:type=" + name);
                if (!mbs.isRegistered(objName)) {
                    mbs.registerMBean(mbean, objName);
                    System.out.println("✔️ MBean registered: " + objName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void unregisterBean(Object mbean) {
        ObjectName objName = beans.remove(mbean);
        if (objName == null) {
            System.err.println("⚠️ MBean not found: " + mbean.getClass().getName());
            return;
        }

        try {
            if (mbs.isRegistered(objName)) {
                mbs.unregisterMBean(objName);
                System.out.println("❌ MBean unregistered: " + objName);
            }
        } catch (InstanceNotFoundException | MBeanRegistrationException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() throws IOException {
        System.setProperty("com.sun.management.jmxremote", "true");
        System.setProperty("com.sun.management.jmxremote.port", "9990");
        System.setProperty("com.sun.management.jmxremote.authenticate", "false");
        System.setProperty("com.sun.management.jmxremote.ssl", "false");

        registerBean(new PointMonitor(), "PointMonitor");
        registerBean(new AreaSquare(), "AreaSquare");
    }

    @PreDestroy
    public void destroy() {
        for (Object bean : beans.keySet().toArray()) {
            unregisterBean(bean);
        }
    }
}
