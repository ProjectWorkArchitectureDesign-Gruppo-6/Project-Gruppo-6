package StrategyTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Strategy.RectangleSelectionStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RectangleSelectionStrategyTest {

    private RectangleSelectionStrategy strategy;
    private Shape mockShape;

    @BeforeEach
    void setUp() {
        strategy = new RectangleSelectionStrategy();
        mockShape = mock(Shape.class);

        // Coordinate simulate
        when(mockShape.getXc()).thenReturn(50.0);
        when(mockShape.getYc()).thenReturn(100.0);
        when(mockShape.getDim1()).thenReturn(80.0);
        when(mockShape.getDim2()).thenReturn(40.0);
    }

    @Test
    void testGetMoveButtonX() {
        double buttonWidth = 20.0; // valore della superclasse
        double expected = 50.0 + (80.0 - buttonWidth) / 2;

        double actual = strategy.getMoveButtonX(mockShape);
        assertEquals(expected, actual, 0.001);
    }

    @Test
    void testGetMoveButtonY() {
        double expected = 100.0 + 40.0 + 5; // Y + height + 5
        double actual = strategy.getMoveButtonY(mockShape);
        assertEquals(expected, actual, 0.001);
    }
}