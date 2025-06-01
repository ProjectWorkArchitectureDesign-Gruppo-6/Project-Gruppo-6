package StateTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.RotateCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.RotationState;
import projectworkgroup6.State.SingleSelectState;

import static org.mockito.Mockito.*;

public class RotationStateTest {

    private Shape mockShape;
    private SelectedDecorator mockShapeView;
    private RotateCommand mockRotateCommand;
    private RotationState rotationState;
    private StateController controllerMock;
    private CommandManager commandManagerMock;

    @BeforeEach
    void setUp() {
        mockShape = mock(Shape.class);
        mockShapeView = mock(SelectedDecorator.class);
        when(mockShapeView.getShape()).thenReturn(mockShape);

        mockRotateCommand = mock(RotateCommand.class);

        when(mockShape.getXc()).thenReturn(50.0);
        when(mockShape.getYc()).thenReturn(50.0);
        when(mockShape.getDim1()).thenReturn(20.0);
        when(mockShape.getDim2()).thenReturn(30.0);
        when(mockShape.getRotation()).thenReturn(0.0);

        controllerMock = mock(StateController.class);
        StateController.setInstance(controllerMock);

        commandManagerMock = mock(CommandManager.class);
        CommandManager.setInstance(commandManagerMock);

        rotationState = new RotationState(mockShapeView);
        rotationState.setRotateCommand(mockRotateCommand);
    }

    @Test
    void testHandleMouseDragged_PerformsRotation() {
        rotationState.startRotating(70, 70);

        rotationState.handleMouseDragged(60, 80);

        verify(mockShape).setRotation(anyDouble());
        verify(mockRotateCommand).finalizeRotation();
        verify(controllerMock).notifyCanvasToRepaint();
    }

    @Test
    void testHandleMouseReleased_ExecutesCommandAndResetsState() {
        rotationState.startRotating(70, 70);

        rotationState.handleMouseReleased(70, 70);

        verify(commandManagerMock).executeCommand(mockRotateCommand);
        verify(controllerMock).setState(any(SingleSelectState.class));
    }
}