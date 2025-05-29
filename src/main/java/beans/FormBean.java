package beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import managedBeaans.AreaCheck;
import managedBeaans.DataBaseManager;
import managedBeaans.Validation;
import mbeans.AreaSquare;
import mbeans.MBeanRegistry;
import mbeans.PointMonitor;
import models.Point;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.awt.geom.Area;
import java.io.IOException;
import java.io.Serializable;

@Named("formBean")
@Getter
@Setter
@ApplicationScoped
public class FormBean implements Serializable {
    @Inject
    Validation validation;

    @Inject
    AreaCheck areaCheck;

    @Inject
    PointsContainer pointsContainer;

    @EJB
    DataBaseManager dataBaseManager;

    private Double x;

    private Double y;

    private Double r;



    @Transactional
    public String submit(){
        if (validation.validate(x, y, r)){
            Point point = new Point(x,y,r, areaCheck.isInArea(x, y, r));
            System.out.println(point);
            dataBaseManager.insertIntoTable(point);
            pointsContainer.getPoints().add(0, point);
        }
        return null;
    }


}
