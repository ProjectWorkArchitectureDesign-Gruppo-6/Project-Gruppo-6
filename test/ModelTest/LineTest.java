package ModelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Line;

import static org.junit.jupiter.api.Assertions.*;

public class LineTest {

    private Line line;

    @BeforeEach
    public void setUp() {
        // Linea da (10, 20) a (30, 40)
        line = new Line(10, 20, false, 30, 40,new ColorModel(0,0,0,1),null);
    }

    @Test
    public void testInitialCoordinates() {
        assertEquals(10, line.getX());
        assertEquals(20, line.getY());
    }

    @Test
    public void testMove() {
        line.move(5, -5);
        assertEquals(15, line.getX(), 0.001);
        assertEquals(15, line.getY(), 0.001);
    }

    @Test
    public void testResize() {
        double originalLength = Math.hypot(30 - 10, 40 - 20);
        line.resize(2.0);

        // Verifica che le coordinate iniziali non cambino
        assertEquals(10, line.getX(), 0.001);
        assertEquals(20, line.getY(), 0.001);

        // Verifica che la lunghezza sia raddoppiata
        double newLength = Math.hypot(line.getX2() - line.getX(), line.getY2() - line.getY());
        assertEquals(2 * originalLength, newLength, 0.001);
    }

    @Test
    public void testGetDim1AndDim2() {
        double expectedDim1 = 30 - (30 - 10) / 2.0; // 20
        double expectedDim2 = 40 - (40 - 20) / 2.0; // 30
        assertEquals(expectedDim1, line.getDim1(), 0.001);
        assertEquals(expectedDim2, line.getDim2(), 0.001);
    }

    @Test
    public void testGetCenterCoordinates() {
        double expectedXc = 10 - ((30 - 10) / 2.0); // -0
        double expectedYc = 20 - ((40 - 20) / 2.0); // 10
        assertEquals(0, line.getXc(), 0.001);
        assertEquals(10, line.getYc(), 0.001);
    }

    @Test
    public void testContainsPointOnLine() {
        assertTrue(line.contains(20, 30), "Il punto (20,30) dovrebbe trovarsi sulla linea");
    }

    @Test
    public void testContainsPointNearLineWithTolerance() {
        assertTrue(line.contains(19, 30), "Il punto (19,30) è vicino alla linea, dovrebbe essere accettato");
    }

    @Test
    public void testDoesNotContainPointFarFromLine() {
        assertFalse(line.contains(100, 100), "Il punto (100,100) è lontano, non dovrebbe essere contenuto");
    }
}
