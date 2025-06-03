package StateTest;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.InsertCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Factory.TextBoxCreator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Model.TextBox;
import projectworkgroup6.State.EditingTextState;
import projectworkgroup6.State.InsertTextBoxState;
import projectworkgroup6.View.ShapeView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class InsertTextBoxStateTest {

    private TextBoxCreator creator;
    private InsertTextBoxState state;
    private StateController controller;

    @BeforeEach
    void setUp() {
        creator = mock(TextBoxCreator.class);

        controller = mock(StateController.class);
        when(controller.getFontFamily()).thenReturn("Arial");
        when(controller.getFontSize()).thenReturn(12);
        when(controller.getFontColor()).thenReturn(Color.BLACK);

        StateController.setInstance(controller);
        CommandManager.setInstance(mock(CommandManager.class));

        state = new InsertTextBoxState(creator);
    }

    @Test
    void testHandleClickOnExistingTextBoxEntersEditing() {
        // Simula shape già presente e cliccata
        TextBox textBox = mock(TextBox.class);
        when(textBox.contains(100, 200)).thenReturn(true);

        ShapeView view = mock(ShapeView.class);

        Map<Shape, ShapeView> map = new HashMap<>();
        map.put(textBox, view);

        MouseEvent event = mock(MouseEvent.class);
        state.handleClick(event, 100, 200, map);

        verify(textBox).setEditing(true);
        verify(controller).setState(any(EditingTextState.class));
        verify(controller).redrawCanvas();
    }

    @Test
    void testHandleClickOnEmptyAreaCreatesNewTextBox() {
        when(creator.createShape(anyDouble(), anyDouble(), anyDouble(), anyDouble(), any(), any(), anyInt(), anyInt()))
                .thenReturn(mock(TextBox.class));
        when(creator.createShapeView(any())).thenReturn(mock(ShapeView.class));

        Map<Shape, ShapeView> map = new HashMap<>(); // canvas vuoto
        MouseEvent event = mock(MouseEvent.class);

        state.handleClick(event, 100, 100, map);

        verify(creator).setText("");
        verify(CommandManager.getInstance()).executeCommand(any(InsertCommand.class));
        verify(controller).setState(any(EditingTextState.class));
    }

    @Test
    void testRecoverShapesUndecoratesAndReadds() {
        Shape s = mock(Shape.class);
        ShapeView decorated = mock(ShapeView.class);
        ShapeView undecorated = mock(ShapeView.class);
        when(decorated.undecorate()).thenReturn(undecorated);

        Map<Shape, ShapeView> map = new HashMap<>();
        map.put(s, decorated);

        state.recoverShapes(map);

        verify(s).setSelected(false);
        verify(s).setEditing(false);
        verify(s).setGroup(0);
        verify(controller).removeShape(s, decorated);
        verify(controller).addShape(s, undecorated);
    }

    @Test
    void testHandleColorChangedUpdatesInternalState() {
        state.handleColorChanged(Color.BLUE, Color.LIGHTGRAY);
        // Nessuna eccezione: comportamento accettato
    }
}
