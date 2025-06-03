package FactoryTest;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Factory.LineCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Line;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.LineView;
import projectworkgroup6.View.ShapeView;

import static org.junit.jupiter.api.Assertions.*;

public class LineCreatorTest {

    @Test
    public void testSingletonInstance() {
        LineCreator instance1 = LineCreator.getInstance();
        LineCreator instance2 = LineCreator.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testCreateShape() {
        LineCreator creator = LineCreator.getInstance();
        ColorModel border = new ColorModel(0, 0, 0, 1.0);
        ColorModel fill = new ColorModel(255, 255, 255, 1.0);
        Shape shape = creator.createShape(10, 20, 30, 40, border, fill, 1, 0);

        assertTrue(shape instanceof Line);
    }

    @Test
    public void testCreateShapeView() {
        LineCreator creator = LineCreator.getInstance();
        ColorModel border = new ColorModel(0, 0, 0, 1.0);
        ColorModel fill = new ColorModel(255, 255, 255, 1.0);
        Shape shape = creator.createShape(10, 20, 30, 40, border, fill, 1, 0);
        ShapeView view = creator.createShapeView(shape);

        assertNotNull(view);
        assertTrue(view instanceof LineView);
    }
}
