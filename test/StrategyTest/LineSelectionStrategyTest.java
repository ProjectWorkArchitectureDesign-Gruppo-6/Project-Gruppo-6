package StrategyTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Strategy.LineSelectionStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LineSelectionStrategyTest {

    private LineSelectionStrategy strategy;
    private Shape mockShape;

    @BeforeEach
    void setUp() {
        strategy = new LineSelectionStrategy();
        mockShape = mock(Shape.class);

        // Coordinate simulate
        when(mockShape.getXc()).thenReturn(10.0);
        when(mockShape.getDim1()).thenReturn(100.0);
        when(mockShape.getDim2()).thenReturn(200.0);
    }

    @Test
    void testGetMoveButtonXReturnsExpectedValue() {
        double expected = (10.0 + 100.0 - 40) / 2;
        double actual = strategy.getMoveButtonX(mockShape);
        assertEquals(expected, actual, 0.001);
    }

    @Test
    void testGetMoveButtonYReturnsExpectedValue() {
        double expected = 200.0 - 40;
        double actual = strategy.getMoveButtonY(mockShape);
        assertEquals(expected, actual, 0.001);
    }
}