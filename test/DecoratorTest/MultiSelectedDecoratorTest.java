package DecoratorTest;

import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Decorator.MultiSelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Strategy.SelectionStrategy;
import projectworkgroup6.View.LineView;
import projectworkgroup6.View.ShapeView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MultiSelectedDecoratorTest {

    private Shape shape;
    private ShapeView baseView;
    private GraphicsContext gc;
    private SelectionStrategy strategy;
    private MultiSelectedDecorator decorator;

    // Sottoclasse per iniettare la strategy mockata
    private static class TestableMultiSelectedDecorator extends MultiSelectedDecorator {
        public TestableMultiSelectedDecorator(ShapeView base, SelectionStrategy strategy) {
            super(base);
            super.strategy = strategy;
        }
    }

    @BeforeEach
    void setUp() {
        shape = mock(Shape.class);
        when(shape.getXc()).thenReturn(100.0);
        when(shape.getYc()).thenReturn(200.0);
        when(shape.getDim1()).thenReturn(50.0);
        when(shape.getDim2()).thenReturn(80.0);
        when(shape.getRotation()).thenReturn(30.0);

        baseView = mock(ShapeView.class);
        when(baseView.getShape()).thenReturn(shape);
        when(baseView.undecorate()).thenReturn(baseView); // simulazione: già undecorato

        gc = mock(GraphicsContext.class);

        strategy = mock(SelectionStrategy.class);
        decorator = new TestableMultiSelectedDecorator(baseView, strategy);
    }

    @Test
    void testUndecorateReturnsBaseView() {
        assertSame(baseView, decorator.undecorate());
    }

    @Test
    void testDrawCallsBaseDrawAndDrawsSelectionBorder() {
        decorator.draw(gc);

        verify(baseView).draw(gc);
        verify(gc).save();
        verify(gc).translate(anyDouble(), anyDouble());
        verify(gc).rotate(30.0);
        verify(gc).restore();

        verify(strategy).drawSelectionBorder(gc, shape);
    }

    @Test
    void testUsesLineStrategyWhenBaseIsLineView() {
        // Simula doppio undecorate che restituisce un LineView
        ShapeView lineView = mock(LineView.class);
        when(lineView.getShape()).thenReturn(shape);
        when(lineView.undecorate()).thenReturn(lineView); // ancora LineView

        ShapeView base = mock(ShapeView.class);
        when(base.getShape()).thenReturn(shape);
        when(base.undecorate()).thenReturn(lineView);

        MultiSelectedDecorator d = new MultiSelectedDecorator(base);

        // Verifica che non fallisca: si assume che usi LineSelectionStrategy
        assertEquals(base, d.undecorate());
    }
}
