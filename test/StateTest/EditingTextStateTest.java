package StateTest;

import static org.mockito.Mockito.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.EditingTextState;
import projectworkgroup6.View.ShapeView;

import java.util.HashMap;
import java.util.Map;

class EditingTextStateTest {

    private EditingTextState editingTextState;
    private Shape mockShape;
    private ShapeView mockView;
    private StateController mockController;
    private Map<Shape, ShapeView> map;

    @BeforeEach
    void setUp() {
        mockShape = mock(Shape.class);
        mockView = mock(ShapeView.class);
        mockController = mock(StateController.class);

        when(mockShape.getText()).thenReturn("Hello");
        when(mockShape.contains(50, 50)).thenReturn(true);
        when(mockShape.contains(200, 200)).thenReturn(false);

        editingTextState = new EditingTextState(mockShape, mockView);

        map = new HashMap<>();
        map.put(mockShape, mockView);

        // Set the singleton instance (make sure setInstance exists in StateController)
        StateController.setInstance(mockController);
    }

    @Test
    void testHandleClickInsideShapeDoesNotChangeState() {
        MouseEvent mockMouseEvent = mock(MouseEvent.class);

        editingTextState.handleClick(mockMouseEvent, 50, 50, map);

        verify(mockController, never()).setState(any());
        verify(mockController).redrawCanvas();
        verify(mockShape, never()).setEditing(false);
    }

    @Test
    void testHandleClickOutsideShapeChangesState() {
        MouseEvent mockMouseEvent = mock(MouseEvent.class);

        editingTextState.handleClick(mockMouseEvent, 200, 200, map);

        verify(mockController).setState(any());
        verify(mockShape).setEditing(false);
        verify(mockController).redrawCanvas();
    }

    @Test
    void testHandleKeyTypedAppendCharacter() {
        KeyEvent event = new KeyEvent(KeyEvent.KEY_TYPED, "a", "a", KeyCode.UNDEFINED, false, false, false, false);
        when(mockShape.getText()).thenReturn("Hello");

        editingTextState.handleKeyTyped(event, map);

        verify(mockShape).setText("Helloa");
        verify(mockController).requestCanvasFocus();
        verify(mockController).redrawCanvas();
    }


    @Test
    void testHandleKeyTypedBackspaceWithText() {
        KeyEvent event = new KeyEvent(
                KeyEvent.KEY_TYPED, "\b", "\b", KeyCode.UNDEFINED, false, false, false, false
        );
        when(mockShape.getText()).thenReturn("Hello");

        editingTextState.handleKeyTyped(event, map);

        verify(mockShape).setText("Hell");
        verify(mockController).requestCanvasFocus();
        verify(mockController).redrawCanvas();
    }

    @Test
    void testHandleKeyTypedBackspaceEmptyText() {
        KeyEvent event = new KeyEvent(
                KeyEvent.KEY_TYPED, "\b", "\b", KeyCode.UNDEFINED, false, false, false, false
        );
        when(mockShape.getText()).thenReturn("");

        editingTextState.handleKeyTyped(event, map);

        verify(mockShape).setText("");
        verify(mockController).requestCanvasFocus();
        verify(mockController).redrawCanvas();
    }

    @Test
    void testHandleKeyTypedAppendToEmptyText() {
        KeyEvent event = new KeyEvent(
                KeyEvent.KEY_TYPED, "H", "H", KeyCode.UNDEFINED, false, false, false, false
        );
        when(mockShape.getText()).thenReturn("");

        editingTextState.handleKeyTyped(event, map);

        verify(mockShape).setText("H");
        verify(mockController).requestCanvasFocus();
        verify(mockController).redrawCanvas();
    }
}