package ViewTest;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.View.RectangleView;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleViewTest {

    @Test
    public void testRectangleViewInitialization() {
        Rectangle rectangle = new Rectangle(10, 20, false, 30, 40,
                new ColorModel(255,255,255,1), new ColorModel(0,0,0,1), 1, 1);
        RectangleView view = new RectangleView(rectangle);
        assertNotNull(view);
    }
}