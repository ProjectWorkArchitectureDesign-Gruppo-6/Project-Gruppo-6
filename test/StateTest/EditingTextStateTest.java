package StateTest;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.EditingTextState;
import projectworkgroup6.State.SingleSelectState;
import projectworkgroup6.View.ShapeView;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.*;

class EditingTextStateTest {

    private Shape shape;
    private ShapeView view;
    private EditingTextState editingState;

    @BeforeEach
    void setUp() {
        shape = mock(Shape.class);
        when(shape.getText()).thenReturn("Hello");

        view = mock(ShapeView.class);

        editingState = new EditingTextState(shape, view);

        // Mock del controller singleton
        StateController controller = mock(StateController.class);
        StateController.setInstance(controller);
    }

    @Test
    void testHandleClickOutsideTextBox() {
        MouseEvent event = mock(MouseEvent.class);
        when(shape.contains(50, 50)).thenReturn(false);

        editingState.handleClick(event, 50, 50, Collections.singletonMap(shape, view));

        verify(shape).setEditing(false);
        verify(StateController.getInstance()).setState(SingleSelectState.getInstance());
        verify(StateController.getInstance()).redrawCanvas();
    }

    @Test
    void testHandleClickInsideTextBox() {
        MouseEvent event = mock(MouseEvent.class);
        when(shape.contains(50, 50)).thenReturn(true);

        editingState.handleClick(event, 50, 50, Collections.singletonMap(shape, view));

        verify(StateController.getInstance(), never()).setState(any());
        verify(shape, never()).setEditing(false);
        verify(StateController.getInstance()).redrawCanvas();
    }


}
