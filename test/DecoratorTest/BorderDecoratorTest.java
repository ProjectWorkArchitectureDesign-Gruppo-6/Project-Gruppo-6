package DecoratorTest;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.View.ShapeView;
import projectworkgroup6.Model.Shape;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorderDecoratorTest {

    private ShapeView baseView;
    private Shape mockedShape;
    private BorderDecorator decorator;

    @BeforeEach
    void setUp() {
        mockedShape = mock(Shape.class); // Shape nel tuo progetto
        baseView = mock(ShapeView.class);
        when(baseView.getShape()).thenReturn(mockedShape);
        decorator = new BorderDecorator(baseView, Color.RED);
    }

    @Test
    void testUndecorateReturnsBaseView() {
        ShapeView undecorated = decorator.undecorate();
        assertSame(baseView, undecorated, "undecorate() should return the base view");
    }

    @Test
    void testDrawCallsBaseDrawAndSetsStrokeAndWidth() {
        GraphicsContext gc = mock(GraphicsContext.class);

        decorator.draw(gc);

        // Verifica che abbia impostato il colore e lo spessore del bordo
        verify(gc).setStroke(Color.RED);
        verify(gc).setLineWidth(3.0);

        // Verifica che il draw originale sia stato chiamato
        verify(baseView).draw(gc);
    }
}
