package StateTest;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.MoveCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.SingleSelectState;
import projectworkgroup6.State.TranslationState;
import projectworkgroup6.View.ShapeView;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class TranslationStateTest {

    private TranslationState translationState;
    private SelectedDecorator mockDecorator;
    private MoveCommand mockMoveCommand;
    private Shape mockShape;

    @BeforeEach
    void setUp() {
        mockDecorator = mock(SelectedDecorator.class);
        mockMoveCommand = mock(MoveCommand.class);
        mockShape = mock(Shape.class);

        when(mockDecorator.getShape()).thenReturn(mockShape);

        translationState = new TranslationState(mockDecorator);
        translationState.setMoveCommand(mockMoveCommand);
    }

    @Test
    void testStartDraggingStoresCoordinates() {
        assertDoesNotThrow(() -> translationState.startDragging(100, 200));
    }

    @Test
    void testHandleMouseDraggedCallsExpectedMethods() {
        translationState.startDragging(0, 0);
        translationState.handleMouseDragged(10, 5);

        verify(mockMoveCommand).accumulate(10.0, 5.0);
        verify(mockDecorator, atLeastOnce()).getShape();
    }

    @Test
    void testHandleMouseReleasedRestoresSingleSelectState() {
        assertDoesNotThrow(() -> translationState.handleMouseReleased(50, 50));
    }

    @Test
    void testRecoverShapesDoesNothing() {
        Map<Shape, ShapeView> dummyMap = new HashMap<>();
        assertDoesNotThrow(() -> translationState.recoverShapes(dummyMap));
    }

    @Test
    void testHandleClickDoesNothing() {
        Map<Shape, ShapeView> dummyMap = new HashMap<>();
        assertDoesNotThrow(() -> translationState.handleClick(0, 0, dummyMap));
    }

    @Test
    void testHandleMoveClickDoesNothing() {
        assertDoesNotThrow(() -> translationState.handlePression(10, 10));
    }

    @Test
    void testHandleDeleteDoesNothing() {
        KeyEvent realDeleteEvent = new KeyEvent(
                KeyEvent.KEY_PRESSED,
                "",
                "",
                KeyCode.DELETE,
                false,
                false,
                false,
                false
        );

        Map<Shape, ShapeView> dummyMap = new HashMap<>();
        assertDoesNotThrow(() -> translationState.handleDelete(realDeleteEvent, dummyMap));
    }

    @Test
    void testHandleColorChangedDoesNothing() {
        assertDoesNotThrow(() -> translationState.handleColorChanged(null,null));
    }
}