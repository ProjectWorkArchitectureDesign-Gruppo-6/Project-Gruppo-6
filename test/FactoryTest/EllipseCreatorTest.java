package FactoryTest;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Factory.EllipseCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Ellipse;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.EllipseView;
import projectworkgroup6.View.ShapeView;

import static org.junit.jupiter.api.Assertions.*;

public class EllipseCreatorTest {

    @Test
    public void testSingletonInstance() {
        EllipseCreator instance1 = EllipseCreator.getInstance();
        EllipseCreator instance2 = EllipseCreator.getInstance();
        assertSame(instance1, instance2, "getInstance() should return the same singleton instance");
    }

    @Test
    public void testCreateShapeProperties() {
        EllipseCreator creator = EllipseCreator.getInstance();
        ColorModel border = new ColorModel(255, 0, 0, 1.0);
        ColorModel fill = new ColorModel(0, 255, 0, 1.0);

        Shape shape = creator.createShape(100.0, 200.0, 50.0, 75.0, border, fill, 2, 3);

        assertTrue(shape instanceof Ellipse, "Shape should be an instance of Ellipse");
        Ellipse ellipse = (Ellipse) shape;

        assertEquals(100.0, ellipse.getX());
        assertEquals(200.0, ellipse.getY());
        assertEquals(50.0, ellipse.getDim1());  // width
        assertEquals(75.0, ellipse.getDim2());  // height
        assertEquals("Ellipse", ellipse.type());
    }

    @Test
    public void testCreateShapeView() {
        EllipseCreator creator = EllipseCreator.getInstance();
        ColorModel border = new ColorModel(0, 0, 0, 1.0);
        ColorModel fill = new ColorModel(255, 255, 255, 1.0);
        Shape shape = creator.createShape(0, 0, 10, 20, border, fill, 0, 0);

        ShapeView view = creator.createShapeView(shape);

        assertNotNull(view, "View should not be null");
        assertTrue(view instanceof EllipseView, "View should be an instance of EllipseView");
    }
}
