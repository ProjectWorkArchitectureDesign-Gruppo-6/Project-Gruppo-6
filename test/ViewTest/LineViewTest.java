package ViewTest;

import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.Line;
import projectworkgroup6.View.LineView;

import static org.mockito.Mockito.*;

public class LineViewTest {

    private Line line;
    private LineView lineView;
    private GraphicsContext gc;

    @BeforeEach
    public void setUp() {
        // Creo una linea con coordinate di test
        line = new Line(5, 10, false, 50, 60); // xc=5, yc=10, dim1=50, dim2=60
        lineView = new LineView(line);
        gc = mock(GraphicsContext.class);
    }

    @Test
    public void testDraw_callsStrokeLineWithCorrectParameters() {
        lineView.draw(gc);

        // Verifico che strokeLine venga chiamato con i parametri corretti
        verify(gc).strokeLine(line.getXc(), line.getYc(), line.getDim1(), line.getDim2());
    }
}
