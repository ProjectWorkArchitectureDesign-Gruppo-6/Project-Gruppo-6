package Test;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.SingleSelectState;
import projectworkgroup6.View.ShapeView;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class SingleSelectStateTest {

    private SingleSelectState state;
    private Map<Shape, ShapeView> shapeMap;

    @BeforeEach
    void setUp() {
        state = SingleSelectState.getInstance(); // singleton
        shapeMap = new HashMap<>();
    }

    @Test
    void testHandleClickSelectsShape() {
        Shape shape = mock(Shape.class);
        when(shape.contains(10, 10)).thenReturn(true);
        when(shape.isSelected()).thenReturn(false);
        ShapeView view = mock(ShapeView.class);

        shapeMap.put(shape, view);

        assertDoesNotThrow(() -> state.handleClick(10, 10, shapeMap));
        verify(shape).setSelected(true);
    }

    @Test
    void testHandleClickDeselectsShapeIfAlreadySelected() {
        Shape shape = mock(Shape.class);
        when(shape.contains(5, 5)).thenReturn(true);
        when(shape.isSelected()).thenReturn(true);
        ShapeView view = mock(ShapeView.class);

        shapeMap.put(shape, view);

        assertDoesNotThrow(() -> state.handleClick(5, 5, shapeMap));
        verify(shape).setSelected(false);
    }

    @Test
    void testHandleMoveClickDoesNotThrowWhenNothingSelected() {
        assertDoesNotThrow(() -> state.handleMoveClick(0, 0));
    }

    @Test
    void testHandleMouseDraggedDoesNotThrow() {
        assertDoesNotThrow(() -> state.handleMouseDragged(1, 1));
    }

    @Test
    void testHandleMouseReleasedDoesNotThrow() {
        assertDoesNotThrow(() -> state.handleMouseReleased(2, 2));
    }

    @Test
    void testRecoverShapesDoesNotThrow() {
        Shape shape = mock(Shape.class);
        ShapeView view = mock(ShapeView.class);
        shapeMap.put(shape, view);

        assertDoesNotThrow(() -> state.recoverShapes(shapeMap));
    }

    @Test
    void testHandleDeleteWhenSelectedShapeMatches() {
        Shape shape = mock(Shape.class);
        ShapeView view = mock(ShapeView.class);
        SelectedDecorator decorator = mock(SelectedDecorator.class);

        shapeMap.put(shape, decorator);

        KeyEvent deleteEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.DELETE, false, false, false, false);

        // imposta lo stato con una shape selezionata
        state.handleClick(0, 0, shapeMap); // simula selezione
        // ora simula il delete
        assertDoesNotThrow(() -> state.handleDelete(deleteEvent, shapeMap));
    }
}
