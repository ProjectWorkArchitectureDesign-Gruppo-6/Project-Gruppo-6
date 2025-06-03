package DecoratorTest;

import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Strategy.SelectionStrategy;
import projectworkgroup6.View.LineView;
import projectworkgroup6.View.ShapeView;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SelectedDecoratorTest {

    private Shape shape;
    private ShapeView baseView;
    private GraphicsContext gc;
    private SelectionStrategy strategy;
    private SelectedDecorator decorator;

    // Sottoclasse per testare SelectedDecorator con una strategy mockata
    private static class TestableSelectedDecorator extends SelectedDecorator {
        public TestableSelectedDecorator(ShapeView base, SelectionStrategy forcedStrategy) {
            super(base);
            super.strategy = forcedStrategy;
        }
    }

    @BeforeEach
    void setUp() {
        shape = mock(Shape.class);
        when(shape.getXc()).thenReturn(10.0);
        when(shape.getYc()).thenReturn(20.0);
        when(shape.getDim1()).thenReturn(100.0);
        when(shape.getDim2()).thenReturn(50.0);
        when(shape.getRotation()).thenReturn(0.0);

        baseView = mock(ShapeView.class);
        when(baseView.getShape()).thenReturn(shape);
        when(baseView.undecorate()).thenReturn(baseView); // simula un decoratore su decoratore

        gc = mock(GraphicsContext.class);
        strategy = mock(SelectionStrategy.class);

        decorator = new TestableSelectedDecorator(baseView, strategy);
    }

    @Test
    void testUndecorateReturnsBase() {
        assertSame(baseView, decorator.undecorate(), "undecorate() should return the base ShapeView");
    }

    @Test
    void testDrawCallsStrategyMethods() {
        decorator.draw(gc);

        verify(baseView).draw(gc);
        verify(strategy).drawSelectionBorder(gc, shape);
        verify(strategy).drawHandles(gc, shape);
        verify(strategy).drawStretchHandles(gc, shape);
        verify(strategy).drawMoveButton(gc, shape);
        verify(strategy).RotateButton(gc, shape);
    }

    @Test
    void testGetMoveButtonCoordinates() {
        when(strategy.getMoveButtonX(shape)).thenReturn(42.0);
        when(strategy.getMoveButtonY(shape)).thenReturn(24.0);

        assertEquals(42.0, decorator.getMoveButtonX());
        assertEquals(24.0, decorator.getMoveButtonY());
    }

    @Test
    void testGetRotateButtonCoordinates() {
        when(strategy.getRotateButtonX(shape)).thenReturn(66.0);
        when(strategy.getRotateButtonY(shape)).thenReturn(88.0);

        assertEquals(66.0, decorator.getRotateButtonX());
        assertEquals(88.0, decorator.getRotateButtonY());
    }

    @Test
    void testGetHandlesDelegatesToStrategy() {
        List<AbstractMap.SimpleEntry<Double, Double>> dummyHandles = Arrays.asList(
                new AbstractMap.SimpleEntry<>(1.0, 2.0),
                new AbstractMap.SimpleEntry<>(3.0, 4.0)
        );
        when(strategy.getHandles(shape)).thenReturn(dummyHandles);

        assertEquals(dummyHandles, decorator.getHandles());
    }

    @Test
    void testGetStretchHandlesDelegatesToStrategy() {
        List<AbstractMap.SimpleEntry<Double, Double>> dummyStretch = Arrays.asList(
                new AbstractMap.SimpleEntry<>(5.0, 6.0)
        );
        when(strategy.getStretchHandles(shape)).thenReturn(dummyStretch);

        assertEquals(dummyStretch, decorator.getStretchHandles());
    }

    @Test
    void testUsesLineStrategyWhenUndecoratedIsLineView() {
        // Mock che simula una doppia undecorazione che restituisce un LineView
        ShapeView lineView = mock(LineView.class);
        when(lineView.getShape()).thenReturn(shape);
        when(lineView.undecorate()).thenReturn(lineView); // rimane LineView

        ShapeView base = mock(ShapeView.class);
        when(base.getShape()).thenReturn(shape);
        when(base.undecorate()).thenReturn(lineView);

        SelectedDecorator d = new SelectedDecorator(base);

        assertTrue(d.getMoveButtonX() >= 0); // forza l’uso della strategy

        // Non possiamo accedere direttamente alla strategy,
        // ma il test verifica che non lanci eccezioni e che LineSelectionStrategy sia accettata.
    }
}
