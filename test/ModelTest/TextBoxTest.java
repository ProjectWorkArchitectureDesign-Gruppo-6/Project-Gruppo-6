package ModelTest;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Factory.TextBoxCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.TextBox;
import projectworkgroup6.Model.Shape;

import static org.junit.jupiter.api.Assertions.*;

public class TextBoxTest {

    @Test
    public void testCreation() {
        TextBoxCreator creator = TextBoxCreator.getInstance();
        ColorModel border = new ColorModel(0, 0, 0, 1.0);
        ColorModel fill = new ColorModel(255, 255, 255, 1.0);
        Shape shape = creator.createShape(10, 20, 30, 40, border, fill, 1, 0);
        assertTrue(shape instanceof TextBox);
    }

    @Test
    public void testMoveResize() {
        TextBoxCreator creator = TextBoxCreator.getInstance();
        ColorModel border = new ColorModel(0, 0, 0, 1.0);
        ColorModel fill = new ColorModel(255, 255, 255, 1.0);
        TextBox shape = (TextBox) creator.createShape(10, 20, 30, 40, border, fill, 1, 0);
        shape.move(5, 5);
        assertEquals(15, shape.getX(), 0.01);
        assertEquals(25, shape.getY(), 0.01);
        shape.resize(2.0, 2.0, 0, 0);  // verifica ingrandimento
        assertTrue(shape.getDim1() >= 60 || shape.getDim2() >= 80);
    }

    @Test
    public void testCloneAt() {
        TextBoxCreator creator = TextBoxCreator.getInstance();
        ColorModel border = new ColorModel(0, 0, 0, 1.0);
        ColorModel fill = new ColorModel(255, 255, 255, 1.0);
        TextBox shape = (TextBox) creator.createShape(10, 20, 30, 40, border, fill, 1, 0);
        Shape cloned = shape.cloneAt(100, 200, 2);
        assertNotNull(cloned);
        assertTrue(cloned instanceof TextBox);
    }
}
