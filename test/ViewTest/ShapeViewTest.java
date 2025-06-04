package ViewTest;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.View.RectangleView;

import static org.junit.jupiter.api.Assertions.*;

public class ShapeViewTest {

    @Test
    public void testShapeViewGetShape() {
        Rectangle rectangle = new Rectangle();
        RectangleView view = new RectangleView(rectangle);
        assertEquals(rectangle, view.getShape());
    }
}