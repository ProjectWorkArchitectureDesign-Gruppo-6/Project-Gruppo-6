package CommandTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.InsertCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import static org.mockito.Mockito.*;

class InsertCommandTest {

    private Shape mockShape;
    private ShapeView mockView;
    private StateController mockController;

    @BeforeEach
    void setUp() {
        mockShape = mock(Shape.class);
        mockView = mock(ShapeView.class);
        mockController = mock(StateController.class);

    }

    @Test
    void testExecuteCallsAddShape() {
        InsertCommand command = new InsertCommand(mockShape, mockView);
        command.execute();
        verify(mockController).addShape(mockShape, mockView);
    }

    @Test
    void testUndoCallsRemoveShape() {
        InsertCommand command = new InsertCommand(mockShape, mockView);
        command.undo();
        verify(mockController).removeShape(mockShape, mockView);
    }
}
