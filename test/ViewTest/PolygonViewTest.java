package ViewTest;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Factory.PolygonCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Polygon;
import projectworkgroup6.View.PolygonView;

import static org.junit.jupiter.api.Assertions.*;

public class PolygonViewTest {

    @Test
    public void testPolygonViewInitialization() {
        PolygonCreator creator = PolygonCreator.getInstance();
        creator.resetVertices();
        creator.addVertex(0, 0);
        creator.addVertex(10, 0);
        creator.addVertex(10, 10);
        creator.addVertex(0, 10);
        Polygon polygon = (Polygon) creator.createShape(0,0,0,0,new ColorModel(),new ColorModel(),1,1);
        PolygonView view = new PolygonView(polygon);
        assertNotNull(view);
    }
}