package ModelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.Rectangle;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    private Rectangle rectangle;

    @BeforeEach
    void setUp() {
        // Centro (50, 50), larghezza 40, altezza 20
        rectangle = new Rectangle(50, 50, false, 40, 20);
    }

    @Test
    void testGetDim1() {
        assertEquals(40, rectangle.getDim1());
    }

    @Test
    void testGetDim2() {
        assertEquals(20, rectangle.getDim2());
    }

    @Test
    void testGetXc() {
        assertEquals(30, rectangle.getXc()); // 50 - 40/2
    }

    @Test
    void testGetYc() {
        assertEquals(40, rectangle.getYc()); // 50 - 20/2
    }

    @Test
    void testMove() {
        rectangle.move(10, -5);
        assertEquals(60, rectangle.getX());
        assertEquals(45, rectangle.getY());
    }

    @Test
    void testResize() {
        rectangle.resize(2.0);
        assertEquals(80, rectangle.getDim1());
        assertEquals(40, rectangle.getDim2());
    }

    @Test
    void testContainsPointInside() {
        // Centro della figura
        assertTrue(rectangle.contains(50, 50));
    }

    @Test
    void testContainsPointOutside() {
        assertFalse(rectangle.contains(10, 10));
    }

    @Test
    void testContainsOnBorder() {
        // Lati estremi del rettangolo
        assertTrue(rectangle.contains(30, 50)); // lato sinistro
        assertTrue(rectangle.contains(70, 50)); // lato destro
        assertTrue(rectangle.contains(50, 40)); // alto
        assertTrue(rectangle.contains(50, 60)); // basso
    }
}
