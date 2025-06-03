package ViewTest;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Ellipse;
import projectworkgroup6.View.EllipseView;

import static org.junit.jupiter.api.Assertions.*;

public class EllipseViewTest {

    @Test
    public void testEllipseViewInitialization() {
        Ellipse ellipse = new Ellipse(10, 20, false, 30, 40,
                new ColorModel(255,0,0,1), new ColorModel(0,255,0,1), 1, 1);
        EllipseView view = new EllipseView(ellipse);
        assertNotNull(view);
    }
}