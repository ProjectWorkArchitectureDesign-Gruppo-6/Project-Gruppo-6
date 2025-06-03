package FactoryTest;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Factory.TextBoxCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.TextBox;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.TextBoxView;
import projectworkgroup6.View.ShapeView;

import static org.junit.jupiter.api.Assertions.*;

public class TextBoxCreatorTest {

    @Test
    public void testSingletonInstance() {
        TextBoxCreator instance1 = TextBoxCreator.getInstance();
        TextBoxCreator instance2 = TextBoxCreator.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testCreateShape() {
        TextBoxCreator creator = TextBoxCreator.getInstance();
        ColorModel border = new ColorModel(0, 0, 0, 1.0);
        ColorModel fill = new ColorModel(255, 255, 255, 1.0);
        Shape shape = creator.createShape(10, 20, 30, 40, border, fill, 1, 0);

        assertTrue(shape instanceof TextBox);
    }

    @Test
    public void testCreateShapeView() {
        TextBoxCreator creator = TextBoxCreator.getInstance();
        ColorModel border = new ColorModel(0, 0, 0, 1.0);
        ColorModel fill = new ColorModel(255, 255, 255, 1.0);
        Shape shape = creator.createShape(10, 20, 30, 40, border, fill, 1, 0);
        ShapeView view = creator.createShapeView(shape);

        assertNotNull(view);
        assertTrue(view instanceof TextBoxView);
    }
}
