package ViewTest;

import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Ellipse;
import projectworkgroup6.View.EllipseView;

import static org.mockito.Mockito.*;

public class EllipseViewTest {

    private Ellipse ellipse;
    private EllipseView ellipseView;
    private GraphicsContext gc;

    @BeforeEach
    public void setUp() {
        ellipse = new Ellipse(10, 20, false, 30, 40,new ColorModel(0,0,0,1), new ColorModel(255,255,255,1)); // xc=10, yc=20, dim1=30, dim2=40
        ellipseView = new EllipseView(ellipse);
        gc = mock(GraphicsContext.class);
    }

    @Test
    public void testDraw_callsStrokeOvalWithCorrectParameters() {
        ellipseView.draw(gc);

        // Verifica che strokeOval sia chiamato con i parametri dell’ellisse

        verify(gc).strokeOval(ellipse.getXc(), ellipse.getYc(), ellipse.getDim1(), ellipse.getDim2());
    }
}
