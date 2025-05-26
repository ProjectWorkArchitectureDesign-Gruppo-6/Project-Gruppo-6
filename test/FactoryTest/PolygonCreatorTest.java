package FactoryTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Factory.PolygonCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Polygon;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.PolygonView;
import projectworkgroup6.View.ShapeView;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PolygonCreatorTest {

    private PolygonCreator creator;

    @BeforeEach
    void setUp() {
        creator = PolygonCreator.getInstance();
        creator.resetVertices(); // resetto prima di ogni test
    }

    @Test
    void testSingletonInstance() {
        PolygonCreator anotherInstance = PolygonCreator.getInstance();
        assertSame(creator, anotherInstance, "Deve restituire la stessa istanza");
    }

    @Test
    void testAddAndGetTempVertices() {
        creator.addVertex(1.0, 2.0);
        creator.addVertex(3.0, 4.0);

        ArrayList<double[]> vertices = creator.getTempVertices();
        assertEquals(2, vertices.size());
        assertArrayEquals(new double[]{1.0, 2.0}, vertices.get(0), 0.001);
        assertArrayEquals(new double[]{3.0, 4.0}, vertices.get(1), 0.001);
    }

    @Test
    void testResetVertices() {
        creator.addVertex(1.0, 2.0);
        assertFalse(creator.getTempVertices().isEmpty());

        creator.resetVertices();
        assertTrue(creator.getTempVertices().isEmpty(), "La lista dei vertici temporanei deve essere vuota");
    }

    @Test
    void testCreateShapeCopiesVertices() {
        creator.addVertex(1, 1);
        creator.addVertex(2, 2);

        ColorModel border = new ColorModel(0, 0, 0, 1);
        ColorModel fill = new ColorModel(255, 255, 255, 1);

        Shape shape = creator.createShape(0, 0, border, fill);
        assertTrue(shape instanceof Polygon);

        Polygon polygon = (Polygon) shape;
        assertEquals(2, polygon.getVertices().size());
        assertArrayEquals(new double[]{1, 1}, polygon.getVertices().get(0), 0.001);
    }

    @Test
    void testCreateShapeViewReturnsCorrectType() {
        creator.addVertex(0, 0);
        creator.addVertex(1, 0);
        creator.addVertex(1, 1);

        Shape polygon = creator.createShape(0, 0,
                new ColorModel(0, 0, 0, 1),
                new ColorModel(255, 255, 255, 1));
        ShapeView view = creator.createShapeView(polygon);

        assertNotNull(view);
        assertTrue(view instanceof PolygonView, "La view deve essere un'istanza di PolygonView");
    }
}
