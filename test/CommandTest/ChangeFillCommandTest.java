package CommandTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.ChangeFillCommand;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;

import static org.mockito.Mockito.*;

public class ChangeFillCommandTest {

    private Shape mockShape;
    private ColorModel oldFill;
    private ColorModel newFill;
    private ChangeFillCommand command;

    @BeforeEach
    public void setUp() {
        mockShape = mock(Shape.class);
        oldFill = mock(ColorModel.class);
        newFill = mock(ColorModel.class);

        // Simula il colore attuale della shape
        when(mockShape.getFill()).thenReturn(oldFill);

        command = new ChangeFillCommand(mockShape, newFill);
    }

    @Test
    public void testExecuteSetsNewFill() {
        command.execute();
        verify(mockShape).setFill(newFill);
    }

    @Test
    public void testUndoRestoresOldFill() {
        command.undo();
        verify(mockShape).setFill(oldFill);
    }
}
