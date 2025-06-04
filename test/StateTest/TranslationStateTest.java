package StateTest;

import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.MoveCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.SingleSelectState;
import projectworkgroup6.State.TranslationState;
import projectworkgroup6.View.ShapeView;

import java.util.Collections;

import static org.mockito.Mockito.*;

class TranslationStateTest {

    private Shape shape;
    private ShapeView shapeView;
    private MoveCommand moveCommand;
    private TranslationState state;
    private StateController controller;

    @BeforeEach
    void setUp() {
        shape = mock(Shape.class);
        shapeView = mock(ShapeView.class);
        when(shapeView.getShape()).thenReturn(shape);

        moveCommand = mock(MoveCommand.class);

        controller = mock(StateController.class);
        StateController.setInstance(controller);
        CommandManager.setInstance(mock(CommandManager.class));

        state = new TranslationState(shapeView);
        state.setMoveCommand(moveCommand);
    }

    @Test
    void testStartDraggingInitializesLastCoordinates() {
        state.startDragging(100.0, 200.0);
        // non verificabile direttamente, ma deve essere chiamato prima di drag
    }

    @Test
    void testHandleMouseDraggedMovesShapeAndAccumulates() {
        state.startDragging(100.0, 200.0);
        state.handleMouseDragged(110.0, 210.0);

        verify(shape).move(10.0, 10.0);
        verify(moveCommand).accumulate(10.0, 10.0);
        verify(controller).notifyCanvasToRepaint();
    }

    @Test
    void testHandleMouseReleasedCommitsMoveCommand() {
        state.handleMouseReleased(150.0, 150.0);

        verify(moveCommand).undo();
        verify(CommandManager.getInstance()).executeCommand(moveCommand);
        verify(controller).notifyShapeSelected(shape);
    }

    @Test
    void testHandleClickReturnsToSingleSelectState() {
        MouseEvent event = mock(MouseEvent.class);
        state.handleClick(event, 50.0, 50.0, Collections.emptyMap());

        verify(controller).setState(SingleSelectState.getInstance());
    }
}
