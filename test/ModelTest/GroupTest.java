package ModelTest;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {

    @Test
    public void testGroupConstructorAndGetters() {
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Ellipse(0, 0, false, 10, 20, new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 0, 0));
        shapes.add(new Rectangle(5, 5, false, 15, 25, new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 0, 0));

        Group group = new Group(shapes, 50, 50, false, 100, 100,
                new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 1, 1);

        assertEquals(2, group.getShapes().size());
        assertEquals(100, group.getWidth());
        assertEquals(100, group.getHeight());
    }

    @Test
    public void testMove() {
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Rectangle(10, 10, false, 10, 10, new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 0, 0));
        Group group = new Group(shapes, 20, 20, false, 40, 40,
                new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 0, 0);

        group.move(10, 5);
        assertEquals(30, group.getX(), 0.01);
        assertEquals(25, group.getY(), 0.01);
        assertEquals(20, group.getShapes().get(0).getX(), 0.01);
        assertEquals(15, group.getShapes().get(0).getY(), 0.01);
    }

    @Test
    public void testAddAndRemoveShape() {
        Group group = new Group(new ArrayList<>(), 0, 0, false, 0, 0,
                new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 0, 0);
        Shape shape = new TextBox(
                0, 0, false, 10, 10,
                new ColorModel(0, 0, 0, 1.0),
                new ColorModel(255, 255, 255, 1.0),
                0, 0,
                "Test",
                "Arial",
                12,
                new ColorModel(0, 0, 0, 1.0)
        );
        group.add(shape);
        assertEquals(1, group.getShapes().size());

        group.remove(shape);
        assertTrue(group.getShapes().isEmpty());
    }

    @Test
    public void testCloneAt() {
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Ellipse(10, 10, false, 20, 30, new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 0, 0));
        Group group = new Group(shapes, 10, 10, false, 20, 30,
                new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 0, 0);

        Group clone = (Group) group.cloneAt(100, 200, 1);
        assertNotNull(clone);
        assertEquals(100, clone.getX(), 0.01);
        assertEquals(200, clone.getY(), 0.01);
        assertEquals(1, clone.getShapes().size());
    }

    @Test
    public void testStrokeSnapshot() {
        List<Shape> shapes = new ArrayList<>();
        ColorModel initial = new ColorModel(10, 20, 30, 1.0);
        ColorModel newColor = new ColorModel(200, 200, 200, 1.0);
        shapes.add(new Rectangle(0, 0, false, 10, 10, initial, initial, 0, 0));

        Group group = new Group(shapes, 0, 0, false, 0, 0, initial, initial, 0, 0);

        Object snapshot = group.getStroke();
        group.setBorder(newColor);

        assertNotEquals(initial.getRed(), ((Rectangle)group.getShapes().get(0)).getBorder().getRed());

        group.setStroke(snapshot);
        assertEquals(initial.getRed(), ((Rectangle)group.getShapes().get(0)).getBorder().getRed());
    }
}
