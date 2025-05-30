package CommandTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Command.RotateCommand;
import projectworkgroup6.Model.Shape;

import static org.mockito.Mockito.*;

class RotateCommandTest {

    private Shape mockShape;
    private StateController mockController;

    @BeforeEach
    void setUp() {
        mockShape = mock(Shape.class);
        mockController = mock(StateController.class);

        StateController.setInstance(mockController);
    }

    @Test
    void testFinalizeRotationStoresNewAngle() {
        when(mockShape.getRotation()).thenReturn(45.0, 90.0);

        RotateCommand command = new RotateCommand(mockShape);
        command.finalizeRotation();
    }

    @Test
    void testUndoSetsOldAngleAndNotifiesCanvas() {
        when(mockShape.getRotation()).thenReturn(30.0);

        RotateCommand command = new RotateCommand(mockShape);

        when(mockShape.getRotation()).thenReturn(90.0);
        command.finalizeRotation();

        command.undo();

        verify(mockShape).setRotation(30.0);
        verify(mockController).notifyCanvasToRepaint();
    }
}