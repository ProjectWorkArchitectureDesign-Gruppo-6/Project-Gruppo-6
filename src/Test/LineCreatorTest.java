package FactoryTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Factory.LineCreator;
import projectworkgroup6.Model.Line;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.LineView;
import projectworkgroup6.View.ShapeView;

public class LineCreatorTest {

    @Test
    void testSingletonInstance() {
        LineCreator instance1 = LineCreator.getInstance();
        LineCreator instance2 = LineCreator.getInstance();
        assertSame(instance1, instance2, "getInstance should return the same instance (singleton)");
    }

    @Test
    void testCreateShape() {
        LineCreator creator = LineCreator.getInstance();
        double x = 10;
        double y = 20;

        Shape shape = creator.createShape(x, y);
        assertTrue(shape instanceof Line, "createShape should return an instance of Line");

    }

    @Test
    void testCreateShapeView() {
        LineCreator creator = LineCreator.getInstance();
        Line line = new Line(5, 5, false, 105, 105);

        ShapeView view = creator.createShapeView(line);
        assertTrue(view instanceof LineView, "createShapeView should return an instance of LineView");
        assertEquals(line, view.getShape(), "LineView should be associated with the Line passed");
    }
}
