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
        assertSame(instance1, instance2, "getInstance should return the same instance");
    }

    @Test
    public void testCreateShape() {
        EllipseCreator creator = EllipseCreator.getInstance();
        double x = 10;
        double y = 20;
        Shape shape = creator.createShape(x, y,new ColorModel(0,0,0,1), new ColorModel(255,255,255,1));

        assertNotNull(shape);
        assertTrue(shape instanceof Ellipse);

        Ellipse ellipse = (Ellipse) shape;
        assertEquals(x, ellipse.getX());
        assertEquals(y, ellipse.getY());
        assertFalse(ellipse.isSelected());
        assertEquals(40, ellipse.getDim2());
        assertEquals(40, ellipse.getDim2());
    }

    @Test
    public void testCreateShapeView() {
        EllipseCreator creator = EllipseCreator.getInstance();
        Ellipse ellipse = new Ellipse(5, 6, false, 80, 40,new ColorModel(0,0,0,1), new ColorModel(255,255,255,1));
        ShapeView view = creator.createShapeView(ellipse);

        assertNotNull(view);
        assertTrue(view instanceof EllipseView);
        assertEquals(ellipse, view.getShape());
    }
}
