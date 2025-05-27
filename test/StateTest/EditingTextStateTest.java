package StateTest;

import static org.mockito.Mockito.*;

import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.EditingTextState;
import projectworkgroup6.View.ShapeView;

import java.util.HashMap;

class EditingTextStateTest {

    private EditingTextState editingTextState;
    private Shape mockShape;
    private ShapeView mockView;
    private StateController mockController;

    @BeforeEach
    void setUp() {
        mockShape = mock(Shape.class);
        mockView = mock(ShapeView.class);

        // Imposta un testo di partenza
        when(mockShape.getText()).thenReturn("Hello");
        // Imposta che il punto (50,50) sia dentro la shape e (200,200) fuori
        when(mockShape.contains(50, 50)).thenReturn(true);
        when(mockShape.contains(200, 200)).thenReturn(false);

        editingTextState = new EditingTextState(mockShape, mockView);

        // Mock static singleton StateController using spy (or mock static if using Mockito 4+)
        mockController = mock(StateController.class);
        // Metodo per sostituire l'istanza singleton (usa riflessione o un pattern di test se disponibile)

    }

    @Test
    void testHandleClickInsideShapeDoesNotChangeState() {
        // Click dentro la shape non cambia lo stato
        editingTextState.handleClick(50, 50, new HashMap<>());
        // Non deve cambiare lo stato
        verify(mockController, never()).setState(any());
        // Ridisegna sempre il canvas
        verify(mockController).redrawCanvas();
        // Non deve impostare editing a false
        verify(mockShape, never()).setEditing(false);
    }

    @Test
    void testHandleClickOutsideShapeChangesState() {
        editingTextState.handleClick(200, 200, new HashMap<>());
        verify(mockController).setState(any()); // Controlla che setState venga chiamato
        verify(mockShape).setEditing(false);
        verify(mockController).redrawCanvas();
    }

    @Test
    void testHandleKeyTypedAppendCharacter() {
        KeyEvent mockEvent = mock(KeyEvent.class);
        when(mockEvent.getCharacter()).thenReturn("a");
        when(mockShape.getText()).thenReturn("Hello");

        editingTextState.handleKeyTyped(mockEvent, new HashMap<>());

        verify(mockShape).setText("Helloa");
        verify(mockEvent).consume();
        verify(mockController).requestCanvasFocus();
        verify(mockController).redrawCanvas();
    }

    @Test
    void testHandleKeyTypedBackspaceWithText() {
        KeyEvent mockEvent = mock(KeyEvent.class);
        when(mockEvent.getCharacter()).thenReturn("\b");
        when(mockShape.getText()).thenReturn("Hello");

        editingTextState.handleKeyTyped(mockEvent, new HashMap<>());

        verify(mockShape).setText("Hell");
        verify(mockEvent).consume();
        verify(mockController).requestCanvasFocus();
        verify(mockController).redrawCanvas();
    }

    @Test
    void testHandleKeyTypedBackspaceEmptyText() {
        KeyEvent mockEvent = mock(KeyEvent.class);
        when(mockEvent.getCharacter()).thenReturn("\b");
        when(mockShape.getText()).thenReturn("");

        editingTextState.handleKeyTyped(mockEvent, new HashMap<>());

        verify(mockShape).setText("");
        verify(mockEvent).consume();
        verify(mockController).requestCanvasFocus();
        verify(mockController).redrawCanvas();
    }

    @Test
    void testHandleKeyTypedAppendToEmptyText() {
        KeyEvent mockEvent = mock(KeyEvent.class);
        when(mockEvent.getCharacter()).thenReturn("H");
        when(mockShape.getText()).thenReturn("");

        editingTextState.handleKeyTyped(mockEvent, new HashMap<>());

        verify(mockShape).setText("H");
        verify(mockEvent).consume();
        verify(mockController).requestCanvasFocus();
        verify(mockController).redrawCanvas();
    }
}
