package FactoryTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Factory.PolygonCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Polygon;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.PolygonView;
import projectworkgroup6.View.ShapeView;

import static org.junit.jupiter.api.Assertions.*;

public class PolygonCreatorTest {

    private PolygonCreator creator;

    @BeforeEach
    public void setup() {
        creator = PolygonCreator.getInstance();
        creator.resetVertices(); // pulizia per evitare interferenze tra test
        creator.addVertex(10, 10);
        creator.addVertex(20, 10);
        creator.addVertex(20, 20);
        creator.addVertex(10, 20);
    }

    @Test
    public void testSingletonInstance() {
        PolygonCreator anotherInstance = PolygonCreator.getInstance();
        assertSame(creator, anotherInstance);
    }

    @Test
    public void testCreateShape() {
        ColorModel border = new ColorModel(0, 0, 0, 1.0);
        ColorModel fill = new ColorModel(255, 255, 255, 1.0);
        Shape shape = creator.createShape(0, 0, 0, 0, border, fill, 1, 0);

        assertTrue(shape instanceof Polygon);
        Polygon polygon = (Polygon) shape;
        assertEquals(4, polygon.getVertices().size());
    }

    @Test
    public void testCreateShapeView() {
        ColorModel border = new ColorModel(100, 100, 100, 1.0);
        ColorModel fill = new ColorModel(200, 200, 200, 1.0);
        Shape shape = creator.createShape(0, 0, 0, 0, border, fill, 2, 0);
        ShapeView view = creator.createShapeView(shape);

        assertNotNull(view);
        assertTrue(view instanceof PolygonView);
    }
}
