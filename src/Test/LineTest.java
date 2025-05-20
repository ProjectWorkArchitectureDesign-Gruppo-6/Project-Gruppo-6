package ModelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.Line;

import static org.junit.jupiter.api.Assertions.*;

public class LineTest {

    private Line line;

    @BeforeEach
    public void setUp() {
        // Linea da (10, 20) a (30, 40)
        line = new Line(10, 20, false, 30, 40);
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
        line.resize(2.0);
        assertEquals(20, line.getX(), 0.001);
        assertEquals(40, line.getY(), 0.001);
        // Nota: x2 e y2 non cambiano in questo resize
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
        assertTrue(line.contains(21, 30), "Il punto (21,30) è vicino alla linea, dovrebbe essere accettato");
    }

    @Test
    public void testDoesNotContainPointFarFromLine() {
        assertFalse(line.contains(100, 100), "Il punto (100,100) è lontano, non dovrebbe essere contenuto");
    }
}
