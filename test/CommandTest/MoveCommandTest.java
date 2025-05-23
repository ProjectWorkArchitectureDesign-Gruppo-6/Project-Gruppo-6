package CommandTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import projectworkgroup6.Command.MoveCommand;
import projectworkgroup6.Model.Shape;

import static org.mockito.Mockito.*;

public class MoveCommandTest {

    private Shape mockShape;
    private MoveCommand moveCommand;

    @BeforeEach
    public void setUp() {
        mockShape = mock(Shape.class);
        moveCommand = new MoveCommand(mockShape);
    }

    @Test
    public void testAccumulateAndUndo() {
        // Accumulo lo spostamento totale
        moveCommand.accumulate(5.0, 10.0);

        // Imposto microstep (che viene usato da execute)
        moveCommand.microstep(2.0, 3.0);

        // Eseguo il comando: dovrebbe chiamare shape.move con microstep
        moveCommand.execute();
        verify(mockShape).move(2.0, 3.0);

        // Undo deve spostare la shape indietro del totale accumulato (negativo)
        moveCommand.undo();
        verify(mockShape).move(-5.0, -10.0);
    }
}
