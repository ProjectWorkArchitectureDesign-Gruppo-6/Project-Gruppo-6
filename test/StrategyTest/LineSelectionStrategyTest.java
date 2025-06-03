package StrategyTest;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Strategy.LineSelectionStrategy;

import java.util.AbstractMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LineSelectionStrategyTest {

    private Shape shape;
    private GraphicsContext gc;
    private LineSelectionStrategy strategy;

    @BeforeEach
    void setUp() {
        shape = mock(Shape.class);
        when(shape.getXc()).thenReturn(10.0);
        when(shape.getYc()).thenReturn(20.0);
        when(shape.getX2()).thenReturn(60.0);
        when(shape.getY2()).thenReturn(80.0);
        when(shape.getX()).thenReturn(30.0);
        when(shape.getY()).thenReturn(40.0);

        gc = mock(GraphicsContext.class);
        strategy = new LineSelectionStrategy();
    }

    @Test
    void testDrawSelectionBorder() {
        strategy.drawSelectionBorder(gc, shape);

        verify(gc).setStroke(Color.BLUE);
        verify(gc).setLineWidth(3.0);
        verify(gc).strokeLine(10.0, 20.0, 60.0, 80.0);
    }

    @Test
    void testDrawHandles() {
        strategy.drawHandles(gc, shape);

        verify(gc).setFill(Color.WHITE);
        verify(gc).setStroke(Color.BLACK);
        // Verifica che disegni due maniglie usando fillOval e strokeOval
        verify(gc, times(2)).fillOval(anyDouble(), anyDouble(), anyDouble(), anyDouble());
        verify(gc, times(2)).strokeOval(anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }

    @Test
    void testDrawMoveButton() {
        strategy.drawMoveButton(gc, shape);

        double expectedX = strategy.getMoveButtonX(shape);
        double expectedY = strategy.getMoveButtonY(shape);

        verify(gc).setFill(Color.WHITE);
        verify(gc).fillRect(expectedX, expectedY, 20.0, 20.0);
        verify(gc).strokeOval(expectedX, expectedY, 20.0, 20.0);
        verify(gc).setFill(Color.BLACK);
        verify(gc).fillText("↔", expectedX + 3, expectedY + 15);
    }

    @Test
    void testDrawRotateButton() {
        strategy.RotateButton(gc, shape);

        double expectedX = strategy.getRotateButtonX(shape);
        double expectedY = strategy.getRotateButtonY(shape);

        verify(gc).setFill(Color.WHITE);
        verify(gc).fillRect(expectedX, expectedY, 20.0, 20.0);
        verify(gc).strokeOval(expectedX, expectedY, 20.0, 20.0);
        verify(gc).setFill(Color.BLACK);
        verify(gc).fillText("⟳", expectedX + 3, expectedY + 15);
    }

    @Test
    void testGetMoveButtonCoordinates() {
        assertEquals(10.0, shape.getXc()); // from mock
        assertEquals(30.0, shape.getX());  // from mock


        assertEquals(10.0, shape.getXc());
        assertEquals(30.0 - 20.0, strategy.getMoveButtonX(shape));
        assertEquals(40.0 + 20.0, strategy.getMoveButtonY(shape));
    }

    @Test
    void testGetRotateButtonCoordinates() {
        double moveX = strategy.getMoveButtonX(shape);
        double moveY = strategy.getMoveButtonY(shape);

        assertEquals(moveX + 20.0, strategy.getRotateButtonX(shape));
        assertEquals(moveY + 12.0, strategy.getRotateButtonY(shape));
    }

    @Test
    void testGetHandles() {
        List<AbstractMap.SimpleEntry<Double, Double>> handles = strategy.getHandles(shape);

        assertEquals(2, handles.size());

        double expectedX1 = 10.0 - 3.0;
        double expectedY1 = 20.0 - 3.0;
        double expectedX2 = 60.0 - 3.0;
        double expectedY2 = 80.0 - 3.0;

        assertEquals(expectedX1, handles.get(0).getKey());
        assertEquals(expectedY1, handles.get(0).getValue());
        assertEquals(expectedX2, handles.get(1).getKey());
        assertEquals(expectedY2, handles.get(1).getValue());
    }

    @Test
    void testGetStretchHandlesReturnsNull() {
        assertNull(strategy.getStretchHandles(shape));
    }
}
