package DecoratorTest;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Decorator.GroupBorderDecorator;
import projectworkgroup6.Model.Group;
import projectworkgroup6.View.ShapeView;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupBorderDecoratorTest {

    private ShapeView baseView;
    private Group group;
    private GraphicsContext gc;
    private GroupBorderDecorator decorator;

    @BeforeEach
    void setUp() {
        group = mock(Group.class);
        when(group.getX()).thenReturn(100.0);
        when(group.getY()).thenReturn(200.0);
        when(group.getWidth()).thenReturn(150.0);
        when(group.getHeight()).thenReturn(100.0);
        when(group.getXc()).thenReturn(100.0);
        when(group.getYc()).thenReturn(200.0);
        when(group.getDim1()).thenReturn(150.0);
        when(group.getDim2()).thenReturn(100.0);
        when(group.getRotation()).thenReturn(45.0);

        baseView = mock(ShapeView.class);
        when(baseView.getShape()).thenReturn(group);

        gc = mock(GraphicsContext.class);

        decorator = new GroupBorderDecorator(baseView);
    }

    @Test
    void testUndecorateReturnsBase() {
        assertSame(baseView, decorator.undecorate(), "undecorate should return the base ShapeView");
    }

    @Test
    void testDrawInvokesBaseDrawAndDrawsBorderAndHandles() {
        decorator.draw(gc);

        verify(baseView).draw(gc);
        verify(gc).save();

        verify(gc).translate(anyDouble(), anyDouble());
        verify(gc).rotate(45.0);

        verify(gc).setStroke(Color.LIGHTGRAY);
        verify(gc).setLineDashes(6.0);
        verify(gc).setLineWidth(2.0);
        verify(gc).strokeRect(
                eq(100.0 - 150.0 / 2),
                eq(200.0 - 100.0 / 2),
                eq(150.0),
                eq(100.0)
        );
        verify(gc).setLineDashes(); // reset
        verify(gc).restore();
    }

    @Test
    void testGetMoveButtonCoordinates() {
        // default RectangleSelectionStrategy
        double expectedX = decorator.getMoveButtonX();
        double expectedY = decorator.getMoveButtonY();
        // solo verifica che non lanci eccezioni
        assertTrue(expectedX >= 0 || expectedY >= 0);
    }

    @Test
    void testGetRotateButtonCoordinates() {
        double expectedX = decorator.getRotateButtonX();
        double expectedY = decorator.getRotateButtonY();
        assertTrue(expectedX >= 0 || expectedY >= 0);
    }

    @Test
    void testGetHandlesNotNull() {
        List<AbstractMap.SimpleEntry<Double, Double>> handles = decorator.getHandles();
        assertNotNull(handles);
    }
}
