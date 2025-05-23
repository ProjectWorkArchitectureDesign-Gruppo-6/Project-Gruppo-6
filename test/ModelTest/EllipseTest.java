package ModelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Ellipse;

import static org.junit.jupiter.api.Assertions.*;

class EllipseTest {

    private Ellipse ellipse;

    @BeforeEach
    void setUp() {
        // Centro (50, 50), diametri 40 e 20
        ellipse = new Ellipse(50, 50, false, 40, 20,new ColorModel(0,0,0,1), new ColorModel(255,255,255,1));
    }

    @Test
    void testGetDim1() {
        assertEquals(40, ellipse.getDim1());
    }

    @Test
    void testGetDim2() {
        assertEquals(20, ellipse.getDim2());
    }

    @Test
    void testGetXc() {
        assertEquals(30, ellipse.getXc()); // 50 - 40/2
    }

    @Test
    void testGetYc() {
        assertEquals(40, ellipse.getYc()); // 50 - 20/2
    }

    @Test
    void testMove() {
        ellipse.move(10, -5);
        assertEquals(60, ellipse.getX());
        assertEquals(45, ellipse.getY());
    }

    @Test
    void testResize() {
        ellipse.resize(2.0);
        assertEquals(80, ellipse.getDim1());
        assertEquals(40, ellipse.getDim2());
    }

    @Test
    void testContainsPointInside() {
        // Punto al centro
        assertTrue(ellipse.contains(50, 50));
    }

    @Test
    void testContainsPointOutside() {
        // Fuori dai bordi
        assertFalse(ellipse.contains(10, 10));
    }

    @Test
    void testContainsOnBorder() {
        // Estremo destro
        assertTrue(ellipse.contains(70, 50));
        // Estremo sinistro
        assertTrue(ellipse.contains(30, 50));
        // Estremo alto
        assertTrue(ellipse.contains(50, 40));
        // Estremo basso
        assertTrue(ellipse.contains(50, 60));
    }
}
