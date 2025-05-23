package DecoratorTest;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.View.ShapeView;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class BorderDecoratorTest {

    private ShapeView baseView;
    private BorderDecorator borderDecorator;
    private GraphicsContext gc;

    @BeforeEach
    public void setUp() {
        baseView = mock(ShapeView.class);
        gc = mock(GraphicsContext.class);
        borderDecorator = new BorderDecorator(baseView, Color.RED);
    }

    @Test
    public void testUndecorateReturnsBaseView() {
        ShapeView result = borderDecorator.undecorate();
        assertSame(baseView, result);
    }

    @Test
    public void testDrawCallsBaseViewDraw() {
        borderDecorator.draw(gc);
        verify(baseView, times(1)).draw(gc);
    }
}
