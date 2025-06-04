package ModelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Factory.PolygonCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Polygon;
import projectworkgroup6.Model.Shape;

import static org.junit.jupiter.api.Assertions.*;

public class PolygonTest {

    private PolygonCreator creator;

    @BeforeEach
    public void setup() {
        creator = PolygonCreator.getInstance();
        creator.resetVertices();
        // Aggiungiamo almeno 3 vertici per definire un poligono valido
        creator.addVertex(0, 0);
        creator.addVertex(10, 0);
        creator.addVertex(10, 10);
    }

    @Test
    public void testPolygonCreation() {
        Shape shape = creator.createShape(0, 0, 0, 0,
                new ColorModel(0, 0, 0, 1),
                new ColorModel(255, 255, 255, 1),
                0, 0);
        assertNotNull(shape);
        assertTrue(shape instanceof Polygon);
        Polygon polygon = (Polygon) shape;
        assertEquals(3, polygon.getVertices().size());
    }

    @Test
    public void testPolygonClone() {
        Shape original = creator.createShape(0, 0, 0, 0,
                new ColorModel(0, 0, 0, 1),
                new ColorModel(255, 255, 255, 1),
                0, 0);
        Shape clone = original.cloneAt(100, 100, 1);
        assertNotNull(clone);
        assertTrue(clone instanceof Polygon);
    }
}
