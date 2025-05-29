package beans;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import managedBeaans.DataBaseManager;
import models.Point;

import java.io.Serializable;
import java.util.ArrayList;

@Setter
@Getter
@Named("points")
@ApplicationScoped
public class PointsContainer implements Serializable {
    private ArrayList<Point> points = new ArrayList<>();

    @EJB
    DataBaseManager dataBaseManager;

}
