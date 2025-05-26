import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.ResizeCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;
import projectworkgroup6.State.ResizeState;
import projectworkgroup6.State.SingleSelectState;

import java.util.Map;

class ResizeStateTest {

    private SelectedDecorator mockShapeView;
    private Shape mockShape;
    private ResizeCommand mockResizeCommand;
    private ResizeState resizeState;

    @BeforeEach
    void setUp() {
        mockShape = mock(Shape.class);
        when(mockShape.getX()).thenReturn(50.0);
        when(mockShape.getY()).thenReturn(50.0);
        when(mockShape.getDim1()).thenReturn(20.0);
        when(mockShape.getDim2()).thenReturn(30.0);

        mockShapeView = mock(SelectedDecorator.class);
        when(mockShapeView.getShape()).thenReturn(mockShape);

        mockResizeCommand = mock(ResizeCommand.class);

        resizeState = new ResizeState(mockShapeView);
        resizeState.setResizeCommand(mockResizeCommand);

        // Mock StateController singleton
        StateController stateController = mock(StateController.class);
        // For static getInstance call, you might need a tool like PowerMockito or refactor your code to inject this dependency
        // Here we assume you can inject or mock StateController.getInstance() in some way

        // When addShape/removeShape called, do nothing (or verify later)
        doNothing().when(stateController).removeShape(any(), any());
        doNothing().when(stateController).addShape(any(), any());
        doNothing().when(stateController).setState(any());

        // Similar for CommandManager singleton
        CommandManager commandManager = mock(CommandManager.class);
        doNothing().when(commandManager).executeCommand(any());
    }

    @Test
    void testHandleMouseDragged_ScaleUp() {
        // Setup initial dragging position (start dragging at bottom right corner)
        resizeState.startDragging(60, 60); // lastX, lastY and startX, startY

        // Move mouse dragged farther from center (scaling up)
        double newX = 70;
        double newY = 70;

        // Call method under test
        resizeState.handleMouseDragged(newX, newY);

        // Verify removeShape and addShape called
        verify(StateController.getInstance()).removeShape(mockShape, mockShapeView);
        verify(StateController.getInstance()).addShape(mockShape, mockShapeView);

        // Verify resize called on shape with some scale factor > 1 (because distance increased)
        verify(mockShape).resize(anyDouble());

        // Verify accumulate called on resize command
        verify(mockResizeCommand).accumulate(anyDouble());

        // lastX and lastY updated inside object - no direct getter so cannot assert here

        // Check that startX and startY shifted as per scaling logic
        // (This is internal state, so you can add getters or test indirectly)
    }

    @Test
    void testHandleMouseReleased_ExecutesCommandAndSetsState() {
        resizeState.handleMouseReleased(0, 0);

        // Verify undo called
        verify(mockResizeCommand).undo();

        // Verify executeCommand called on CommandManager
        verify(CommandManager.getInstance()).executeCommand(mockResizeCommand);

        // Verify state set to SingleSelectState
        verify(StateController.getInstance()).setState(SingleSelectState.getInstance());
    }
}
