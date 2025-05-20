package ViewTest;

import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.View.RectangleView;

import static org.mockito.Mockito.*;

public class RectangleViewTest {

    private Rectangle rectangle;
    private RectangleView rectangleView;
    private GraphicsContext gc;

    @BeforeEach
    public void setUp() {
        // Creo un rettangolo con coordinate e dimensioni di test
        rectangle = new Rectangle(10, 20, false, 100, 50); // xc=10, yc=20, dim1=100, dim2=50
        rectangleView = new RectangleView(rectangle);
        gc = mock(GraphicsContext.class);
    }

    @Test
    public void testDraw_callsStrokeRectWithCorrectParameters() {
        rectangleView.draw(gc);

        // Verifico che strokeRect venga chiamato con i parametri corretti
        verify(gc).strokeRect(rectangle.getXc(), rectangle.getYc(), rectangle.getDim1(), rectangle.getDim2());
    }
}
