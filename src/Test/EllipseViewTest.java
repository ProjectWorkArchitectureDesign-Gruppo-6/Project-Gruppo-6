package Test;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.Ellipse;
import projectworkgroup6.View.EllipseView;

import static org.junit.jupiter.api.Assertions.*;

public class EllipseViewTest {

    @Test
    void testStoresShape() {
        double x = 10;
        double y = 10;
        double diam1 = 20;
        double diam2 = 30;

        Ellipse ellipse = new Ellipse(x, y, false, diam1, diam2);
        EllipseView view = new EllipseView(ellipse);

        // Controlla che la shape contenuta in EllipseView sia quella passata
        assertSame(ellipse, view.getShape());

        // Controlla i calcoli di getXc() e getYc()
        assertEquals(x - diam1 / 2, ellipse.getXc(), "Xc dovrebbe essere x - diam1/2");
        assertEquals(y - diam2 / 2, ellipse.getYc(), "Yc dovrebbe essere y - diam2/2");

        // Controlla i diametri
        assertEquals(diam1, ellipse.getDim1());
        assertEquals(diam2, ellipse.getDim2());
    }

}
