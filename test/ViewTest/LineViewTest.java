package ViewTest;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Line;
import projectworkgroup6.View.LineView;

import static org.junit.jupiter.api.Assertions.*;

public class LineViewTest {

    @Test
    public void testLineViewInitialization() {
        Line line = new Line(0, 0, false, 100, 0,
                new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 1, 1);
        LineView view = new LineView(line);
        assertNotNull(view);
    }
}