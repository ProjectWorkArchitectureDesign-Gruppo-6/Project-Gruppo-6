package DecoratorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Strategy.SelectionStrategy;
import projectworkgroup6.View.ShapeView;
import projectworkgroup6.Model.Shape;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SelectedDecoratorTest {

    private ShapeView base;
    private SelectionStrategy strategy;
    private SelectedDecorator selectedDecorator;
    private Shape shape;

    @BeforeEach
    public void setUp() {
        base = mock(ShapeView.class);
        strategy = mock(SelectionStrategy.class);
        shape = mock(Shape.class);

        when(base.getShape()).thenReturn(shape);
        when(base.undecorate()).thenReturn(base); // per evitare problemi con instanceof in constructor

        // Creiamo una classe anonima per sovrascrivere la strategy del decorator (per testare)
        selectedDecorator = new SelectedDecorator(base) {
            {
                this.strategy = SelectedDecoratorTest.this.strategy;
            }
        };
    }

    @Test
    public void testUndecorateReturnsBase() {
        assertSame(base, selectedDecorator.undecorate());
    }



    @Test
    public void testGetMoveButtonCoordinates() {
        when(strategy.getMoveButtonX(shape)).thenReturn(42.0);
        when(strategy.getMoveButtonY(shape)).thenReturn(84.0);

        assertEquals(42.0, selectedDecorator.getMoveButtonX());
        assertEquals(84.0, selectedDecorator.getMoveButtonY());
    }
}
