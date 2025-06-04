package StateTest;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.StretchCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.SingleSelectState;
import projectworkgroup6.State.StretchState;
import projectworkgroup6.View.ShapeView;

import static org.mockito.Mockito.*;

class StretchStateTest {

    private Shape shape;
    private SelectedDecorator decorator;
    private StretchCommand stretchCommand;
    private StretchState stretchState;
    private StateController controller;

    @BeforeEach
    void setUp() {
        shape = mock(Shape.class);
        when(shape.getX()).thenReturn(100.0);
        when(shape.getY()).thenReturn(100.0);
        when(shape.getXc()).thenReturn(100.0);
        when(shape.getYc()).thenReturn(100.0);
        when(shape.getDim1()).thenReturn(80.0);
        when(shape.getDim2()).thenReturn(40.0);
        when(shape.getRotation()).thenReturn(0.0);

        decorator = mock(SelectedDecorator.class);
        when(decorator.getShape()).thenReturn(shape);

        stretchCommand = mock(StretchCommand.class);
        controller = mock(StateController.class);
        StateController.setInstance(controller);
        CommandManager.setInstance(mock(CommandManager.class));

        stretchState = new StretchState(decorator);
        stretchState.setStretchCommand(stretchCommand);
    }

    @Test
    void testStartDraggingInitializesValues() {
        stretchState.startDragging(150, 150);
        // Non verificabile direttamente, ma non deve causare errori
    }

    @Test
    void testHandleMouseDraggedTriggersStretchRight() {
        // simula che siamo in zona destra
        stretchState.startDragging(140, 100); // start in centro-destra
        stretchState.handleMouseDragged(150, 100); // move verso destra

        verify(shape, atLeastOnce()).stretch(anyDouble(), eq(0.0), eq("RIGHT"));
        verify(stretchCommand, atLeastOnce()).accumulate(anyDouble(), eq(0.0), eq("RIGHT"));
        verify(controller).addShape(shape, decorator);
    }


    @Test
    void testHandleMouseReleasedFinalizesStretch() {
        stretchState.startDragging(100, 100);
        stretchState.handleMouseReleased(120, 120);

        verify(stretchCommand).undo();
        verify(CommandManager.getInstance()).executeCommand(stretchCommand);
        verify(controller).notifyShapeSelected(shape);
    }

    @Test
    void testHandleClickChangesState() {
        MouseEvent e = mock(MouseEvent.class);

        stretchState.handleClick(e, 50, 50, java.util.Collections.emptyMap());

        verify(controller).setState(SingleSelectState.getInstance());
    }
}
