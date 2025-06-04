package ViewTest;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Group;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.View.GroupView;
import projectworkgroup6.View.RectangleView;
import projectworkgroup6.View.ShapeView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupViewTest {

    @Test
    public void testGroupViewInitialization() {
        // Crea una Rectangle shape
        Rectangle rect = new Rectangle(0, 0, false, 10, 10,
                new ColorModel(), new ColorModel(), 1, 1);

        // Crea un Group con quella shape
        List<projectworkgroup6.Model.Shape> shapes = new ArrayList<>();
        shapes.add(rect);
        Group group = new Group(shapes, 10, 20, false, 50, 60,
                new ColorModel(0, 0, 0, 1), new ColorModel(255, 255, 255, 1), 1, 1);

        // Crea la lista delle ShapeView corrispondenti
        List<ShapeView> views = new ArrayList<>();
        views.add(new RectangleView(rect));

        // Passa entrambi al costruttore di GroupView
        GroupView groupView = new GroupView(group, views);

        assertNotNull(groupView);
        assertEquals(1, groupView.getViews().size());
        assertTrue(groupView.getViews().get(0) instanceof RectangleView);
    }
}
