package CommandTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.ResizeCommand;
import projectworkgroup6.Model.Shape;

import static org.mockito.Mockito.*;

public class ResizeCommandTest {

    private Shape mockShape;
    private ResizeCommand resizeCommand;

    @BeforeEach
    public void setUp() {
        mockShape = mock(Shape.class);
        resizeCommand = new ResizeCommand(mockShape);
    }

    @Test
    public void testAccumulateAndExecute() {
        resizeCommand.accumulate(2.0); // raddoppia la dimensione

        resizeCommand.execute();

        // Verifica che shape.resize sia stata chiamata con 2.0
        verify(mockShape).resize(2.0);
    }

    @Test
    public void testUndo() {
        resizeCommand.accumulate(2.0); // raddoppia

        resizeCommand.undo();

        // Undo deve ridimensionare con il reciproco
        verify(mockShape).resize(0.5);
    }
}
