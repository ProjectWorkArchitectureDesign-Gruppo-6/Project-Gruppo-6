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

    private Shape mockShape;
    private ShapeView mockView;
    private StateController mockController;

    @BeforeEach
    void setUp() {
        mockShape = mock(Shape.class);
        mockView = mock(ShapeView.class);
        mockController = mock(StateController.class);
        StateController.setInstance(mockController);
    }

    @Test
    void testExecuteCallsRemoveShape() {
        DeleteCommand command = new DeleteCommand(mockShape, mockView);
        command.execute();
        verify(mockController).removeShape(mockShape, mockView);
    }

    @Test
    void testUndoCallsRemoveShape() {
        ShapeView undecoratedView = mock(ShapeView.class);
        when(mockView.undecorate()).thenReturn(undecoratedView);

        DeleteCommand command = new DeleteCommand(mockShape, mockView);
        command.undo();

        verify(mockController).addShape(mockShape, undecoratedView);
    }
}
