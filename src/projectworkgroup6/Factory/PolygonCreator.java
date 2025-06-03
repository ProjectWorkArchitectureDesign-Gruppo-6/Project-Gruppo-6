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
    public Shape createShape(double x, double y, double width, double height, ColorModel border, ColorModel fill, int layer, int group) {
        // Crea una nuova lista copiando i vertici
        ArrayList<double[]> copiedVertices = new ArrayList<>();
        for (double[] v : tempVertices) {
            copiedVertices.add(new double[]{v[0], v[1]});
        }

        double Xmin = tempVertices.stream().mapToDouble(v->v[0]).min().orElse(x); //si prende il vertice con la coordinata x minima
        double Xmax = tempVertices.stream().mapToDouble(v->v[0]).max().orElse(x);
        width =  Xmax - Xmin;

        double Ymin = tempVertices.stream().mapToDouble(v->v[1]).min().orElse(y);
        double Ymax = tempVertices.stream().mapToDouble(v->v[1]).max().orElse(y);
        height = Ymax - Ymin;

        return new Polygon(copiedVertices, false, width, height, border, fill, layer, group);
    }

    @Override
    public ShapeView createShapeView(Shape shape) {
        return new PolygonView((Polygon) shape);
    }
}
