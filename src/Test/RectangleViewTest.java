package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.View.RectangleView;

public class RectangleViewTest {

    @Test
    void testRectangleProperties() {
        double x = 50;
        double y = 60;
        double width = 100;
        double height = 80;

        Rectangle rect = new Rectangle(x, y, false, width, height);

        assertEquals(width, rect.getDim1(), 0.0001, "getDim1() dovrebbe restituire larghezza");
        assertEquals(height, rect.getDim2(), 0.0001, "getDim2() dovrebbe restituire altezza");

        double expectedXc = x - width / 2;
        double expectedYc = y - height / 2;

        assertEquals(expectedXc, rect.getXc(), 0.0001, "getXc() dovrebbe restituire il bordo sinistro");
        assertEquals(expectedYc, rect.getYc(), 0.0001, "getYc() dovrebbe restituire il bordo superiore");

        // Test move
        rect.move(10, -20);
        assertEquals(x + 10, rect.getX(), 0.0001, "move() X non corretto");
        assertEquals(y - 20, rect.getY(), 0.0001, "move() Y non corretto");

        // Test resize
        rect.resize(2);
        assertEquals(width * 2, rect.getDim1(), 0.0001, "resize() larghezza non corretta");
        assertEquals(height * 2, rect.getDim2(), 0.0001, "resize() altezza non corretta");

        // Test contains
        double insideX = rect.getX(); // centro
        double insideY = rect.getY(); // centro
        assertTrue(rect.contains(insideX, insideY), "contains() dovrebbe restituire true per punto interno");

        double outsideX = rect.getXc() - 10;
        double outsideY = rect.getYc() - 10;
        assertFalse(rect.contains(outsideX, outsideY), "contains() dovrebbe restituire false per punto esterno");
    }

    @Test
    void testRectangleViewStoresShape() {
        Rectangle rect = new Rectangle(20, 30, false, 50, 40);
        RectangleView view = new RectangleView(rect);

        assertSame(rect, view.getShape(), "RectangleView dovrebbe contenere la shape associata");
    }
}
