package FactoryTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Factory.RectangleCreator;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.RectangleView;
import projectworkgroup6.View.ShapeView;

public class RectangleCreatorTest {

    @Test
    void testSingletonInstance() {
        RectangleCreator instance1 = RectangleCreator.getInstance();
        RectangleCreator instance2 = RectangleCreator.getInstance();
        assertSame(instance1, instance2, "getInstance should return the same instance (singleton)");
    }

    @Test
    void testCreateShape() {
        RectangleCreator creator = RectangleCreator.getInstance();
        double x = 15;
        double y = 25;

        Shape shape = creator.createShape(x, y);
        assertTrue(shape instanceof Rectangle, "createShape should return an instance of Rectangle");

        Rectangle rectangle = (Rectangle) shape;
        assertEquals(x, rectangle.getX(), "Rectangle x should be equal to input x");
        assertEquals(y, rectangle.getY(), "Rectangle y should be equal to input y");
        assertEquals(100, rectangle.getDim1(), "Rectangle width should be default 100");
        assertEquals(50, rectangle.getDim2(), "Rectangle height should be default 50");
        assertFalse(rectangle.isSelected(), "New rectangle should not be selected");
    }

    @Test
    void testCreateShapeView() {
        RectangleCreator creator = RectangleCreator.getInstance();
        Rectangle rectangle = new Rectangle(10, 10, false, 100, 50);

        ShapeView view = creator.createShapeView(rectangle);
        assertTrue(view instanceof RectangleView, "createShapeView should return an instance of RectangleView");
        assertEquals(rectangle, view.getShape(), "RectangleView should be associated with the Rectangle passed");
    }
}
