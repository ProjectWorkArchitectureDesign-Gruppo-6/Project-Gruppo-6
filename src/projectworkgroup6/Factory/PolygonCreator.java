package projectworkgroup6.Factory;

import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Polygon;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.PolygonView;
import projectworkgroup6.View.RectangleView;
import projectworkgroup6.View.ShapeView;

import java.util.ArrayList;
import java.util.List;

public class PolygonCreator implements ShapeCreator{

    private static PolygonCreator instance;

    private PolygonCreator() {
        // Costruttore privato per Singleton
    }

    public static PolygonCreator getInstance() {
        if (instance == null) {
            instance = new PolygonCreator();
        }
        return instance;
    }

    private ArrayList<double[]> tempVertices = new ArrayList<>();
    public ArrayList<double[]> getTempVertices() {
        return tempVertices;
    }


    public void addVertex(double x, double y) {
        tempVertices.add(new double[]{x, y});
    }

    public void resetVertices() {
        tempVertices.clear();
    }


    @Override
    public Shape createShape(double x, double y, ColorModel border, ColorModel fill) {
        // Crea una nuova lista copiando i vertici
        ArrayList<double[]> copiedVertices = new ArrayList<>();
        for (double[] v : tempVertices) {
            copiedVertices.add(new double[]{v[0], v[1]});
        }

        return new Polygon(copiedVertices, false, border, fill);
    }

    @Override
    public ShapeView createShapeView(Shape shape) {
        return new PolygonView((Polygon) shape);
    }
}
