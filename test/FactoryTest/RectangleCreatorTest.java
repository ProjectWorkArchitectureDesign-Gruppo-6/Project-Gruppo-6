package FactoryTest;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Factory.RectangleCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.RectangleView;
import projectworkgroup6.View.ShapeView;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleCreatorTest {

    @Test
    public void testSingletonInstance() {
        RectangleCreator instance1 = RectangleCreator.getInstance();
        RectangleCreator instance2 = RectangleCreator.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testCreateShape() {
        RectangleCreator creator = RectangleCreator.getInstance();
        ColorModel border = new ColorModel(0, 0, 0, 1.0);
        ColorModel fill = new ColorModel(255, 255, 255, 1.0);
        Shape shape = creator.createShape(10, 20, 30, 40, border, fill, 1, 0);

        assertTrue(shape instanceof Rectangle);
    }

    @Test
    public void testCreateShapeView() {
        RectangleCreator creator = RectangleCreator.getInstance();
        ColorModel border = new ColorModel(0, 0, 0, 1.0);
        ColorModel fill = new ColorModel(255, 255, 255, 1.0);
        Shape shape = creator.createShape(10, 20, 30, 40, border, fill, 1, 0);
        ShapeView view = creator.createShapeView(shape);

        assertNotNull(view);
        assertTrue(view instanceof RectangleView);
    }
}
