package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.Line;
import projectworkgroup6.View.LineView;

public class LineViewTest {

    @Test
    void testLineProperties() {
        double x1 = 10;
        double y1 = 20;
        double x2 = 30;
        double y2 = 40;

        Line line = new Line(x1, y1, false, x2, y2);

        // Verifica che i metodi restituiscano valori coerenti
        double expectedDim1 = x2 - ((x2 - x1) / 2);
        double expectedDim2 = y2 - ((y2 - y1) / 2);
        double expectedXc = x1 - ((x2 - x1) / 2);
        double expectedYc = y1 - ((y2 - y1) / 2);

        assertEquals(expectedDim1, line.getDim1(), 0.0001, "getDim1() calcolo errato");
        assertEquals(expectedDim2, line.getDim2(), 0.0001, "getDim2() calcolo errato");
        assertEquals(expectedXc, line.getXc(), 0.0001, "getXc() calcolo errato");
        assertEquals(expectedYc, line.getYc(), 0.0001, "getYc() calcolo errato");

        // Test move: spostamento di tutta la linea
        line.move(5, -5);
        assertEquals(x1 + 5, line.getX(), 0.0001, "move() X non corretto");
        assertEquals(y1 - 5, line.getY(), 0.0001, "move() Y non corretto");

        // Test contains(): punto vicino alla linea con tolleranza
        // Usa un punto esattamente sulla linea
        double midX = (line.getX() + line.getDim1()) / 2;
        double midY = (line.getY() + line.getDim2()) / 2;
        assertTrue(line.contains(midX, midY), "contains() dovrebbe ritornare true per punto su linea");

        // Punto fuori tolleranza
        assertFalse(line.contains(midX + 10, midY + 10), "contains() dovrebbe ritornare false per punto lontano dalla linea");
    }

    @Test
    void testLineViewStoresShape() {
        Line line = new Line(10, 10, false, 50, 50);
        LineView view = new LineView(line);

        // Verifica che LineView contenga correttamente la shape
        assertSame(line, view.getShape(), "LineView non contiene la shape corretta");
    }

}
