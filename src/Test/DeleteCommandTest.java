package CommandTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import projectworkgroup6.Command.DeleteCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import static org.mockito.Mockito.*;

public class DeleteCommandTest {

    private Shape shape;
    private ShapeView shapeView;
    private StateController mockStateController;
    private DeleteCommand deleteCommand;

    @BeforeEach
    void setUp() {
        shape = mock(Shape.class);
        shapeView = mock(ShapeView.class);
        mockStateController = mock(StateController.class);

        // Mock statico per intercettare la chiamata a getInstance()
        try (MockedStatic<StateController> mockedStatic = mockStatic(StateController.class)) {
            mockedStatic.when(StateController::getInstance).thenReturn(mockStateController);
            // Ora deleteCommand userà il mock
            deleteCommand = new DeleteCommand(shape, shapeView);
        }
    }

    @Test
    void testExecuteRemovesShape() {
        try (MockedStatic<StateController> mockedStatic = mockStatic(StateController.class)) {
            mockedStatic.when(StateController::getInstance).thenReturn(mockStateController);

            deleteCommand.execute();

            verify(mockStateController).removeShape(shape, shapeView);
        }
    }

    @Test
    void testUndoRestoresShape() {
        try (MockedStatic<StateController> mockedStatic = mockStatic(StateController.class)) {
            mockedStatic.when(StateController::getInstance).thenReturn(mockStateController);

            deleteCommand.undo();

            verify(mockStateController).addShape(shape, shapeView);
        }
    }
}
