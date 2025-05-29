package beans;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import managedBeaans.AreaCheck;
import managedBeaans.DataBaseManager;
import managedBeaans.Validation;
import mbeans.MBeanRegistry;
import models.Point;

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
            double r = point.getR();
            MBeanRegistry.areaSquare.makeAreaSquare(r*r*(1+3.14/4));
            MBeanRegistry.pointMonitor.notifyIfPointOutsideArea(x, y, r);
            System.out.println(point);
            dataBaseManager.insertIntoTable(point);
            pointsContainer.getPoints().add(0, point);
        }
        return null;
    }


}
