package CommandTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.ChangeBorderCommand;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;

import static org.mockito.Mockito.*;

public class ChangeBorderCommandTest {

    private Shape mockShape;
    private ColorModel oldBorder;
    private ColorModel newBorder;
    private ChangeBorderCommand command;

    @BeforeEach
    public void setUp() {
        mockShape = mock(Shape.class);
        oldBorder = mock(ColorModel.class);
        newBorder = mock(ColorModel.class);

        // Simula il colore del bordo iniziale
        when(mockShape.getBorder()).thenReturn(oldBorder);

        command = new ChangeBorderCommand(mockShape, newBorder);
    }

    @Test
    public void testExecuteSetsNewBorder() {
        command.execute();
        verify(mockShape).setBorder(newBorder);
    }

    @Test
    public void testUndoRestoresOldBorder() {
        command.undo();
        verify(mockShape).setBorder(oldBorder);
    }
}
