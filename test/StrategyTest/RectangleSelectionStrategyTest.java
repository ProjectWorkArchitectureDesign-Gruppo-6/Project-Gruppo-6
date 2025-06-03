package StrategyTest;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Strategy.RectangleSelectionStrategy;

import java.util.AbstractMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RectangleSelectionStrategyTest {

    private Shape shape;
    private GraphicsContext gc;
    private RectangleSelectionStrategy strategy;

    @BeforeEach
    void setUp() {
        shape = mock(Shape.class);
        when(shape.getXc()).thenReturn(100.0);
        when(shape.getYc()).thenReturn(200.0);
        when(shape.getDim1()).thenReturn(80.0);  // larghezza
        when(shape.getDim2()).thenReturn(40.0);  // altezza

        gc = mock(GraphicsContext.class);
        strategy = new RectangleSelectionStrategy();
    }

    @Test
    void testDrawSelectionBorder() {
        strategy.drawSelectionBorder(gc, shape);

        verify(gc).setStroke(Color.BLUE);
        verify(gc).setLineWidth(2.0);
        verify(gc).strokeRect(100.0, 200.0, 80.0, 40.0);
    }

    @Test
    void testDrawHandles() {
        strategy.drawHandles(gc, shape);

        verify(gc).setFill(Color.WHITE);
        verify(gc).setStroke(Color.BLACK);
        verify(gc, times(4)).fillOval(anyDouble(), anyDouble(), anyDouble(), anyDouble());
        verify(gc, times(4)).strokeOval(anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }

    @Test
    void testDrawStretchHandles() {
        strategy.drawStretchHandles(gc, shape);

        verify(gc, times(4)).strokeLine(anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }

    @Test
    void testDrawMoveButton() {
        strategy.drawMoveButton(gc, shape);

        double x = strategy.getMoveButtonX(shape);
        double y = strategy.getMoveButtonY(shape);

        verify(gc).setFill(Color.WHITE);
        verify(gc).fillRect(x, y, 20.0, 20.0);
        verify(gc).strokeOval(x, y, 20.0, 20.0);
        verify(gc).setFill(Color.BLACK);
        verify(gc).fillText("<>", x + 3, y + 15);
    }

    @Test
    void testDrawRotateButton() {
        strategy.RotateButton(gc, shape);

        double x = strategy.getRotateButtonX(shape);
        double y = strategy.getRotateButtonY(shape);

        verify(gc).fillRect(x, y, 20.0, 20.0);
        verify(gc).strokeOval(x, y, 20.0, 20.0);
        verify(gc).fillText("⟳", x + 3, y + 15);
    }

    @Test
    void testGetMoveButtonCoordinates() {
        double expectedX = 100.0 + (80.0 - 20.0) / 2;
        double expectedY = 200.0 + 40.0 + 10;

        assertEquals(expectedX, strategy.getMoveButtonX(shape));
        assertEquals(expectedY, strategy.getMoveButtonY(shape));
    }

    @Test
    void testGetRotateButtonCoordinates() {
        double moveX = strategy.getMoveButtonX(shape);
        double moveY = strategy.getMoveButtonY(shape);

        assertEquals(moveX + 25.0, strategy.getRotateButtonX(shape));
        assertEquals(moveY, strategy.getRotateButtonY(shape));
    }

    @Test
    void testGetHandlesCoordinates() {
        List<AbstractMap.SimpleEntry<Double, Double>> handles = strategy.getHandles(shape);

        assertEquals(4, handles.size());

        double half = 3.0;

        // Top-left
        assertEquals(100.0 - half, handles.get(0).getKey());
        assertEquals(200.0 - half, handles.get(0).getValue());

        // Top-right
        assertEquals(100.0 + 80.0 - half, handles.get(1).getKey());
        assertEquals(200.0 - half, handles.get(1).getValue());

        // Bottom-left
        assertEquals(100.0 - half, handles.get(2).getKey());
        assertEquals(200.0 + 40.0 - half, handles.get(2).getValue());

        // Bottom-right
        assertEquals(100.0 + 80.0 - half, handles.get(3).getKey());
        assertEquals(200.0 + 40.0 - half, handles.get(3).getValue());
    }

    @Test
    void testGetStretchHandlesCoordinates() {
        List<AbstractMap.SimpleEntry<Double, Double>> handles = strategy.getStretchHandles(shape);

        assertEquals(4, handles.size());

        double half = 3.0;

        // Top (center)
        assertEquals(100.0 + 80.0 / 2 - half, handles.get(0).getKey());
        assertEquals(200.0 - half, handles.get(0).getValue());

        // Right (center)
        assertEquals(100.0 + 80.0 - half, handles.get(1).getKey());
        assertEquals(200.0 + 40.0 / 2 - half, handles.get(1).getValue());

        // Bottom (center)
        assertEquals(100.0 + 80.0 / 2 - half, handles.get(2).getKey());
        assertEquals(200.0 + 40.0 - half, handles.get(2).getValue());

        // Left (center)
        assertEquals(100.0 - half, handles.get(3).getKey());
        assertEquals(200.0 + 40.0 / 2 - half, handles.get(3).getValue());
    }
}
