//executeCommand() chiami effettivamente execute().
//
//undoLastCommand() chiami undo() dell’ultimo comando inserito.
//
//L'ordine sia LIFO (Last-In-First-Out), (che l'ultimo comando eseguito sia effettivamente quello rimosso).

package CommandTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.Command;
import projectworkgroup6.Command.CommandManager;

import static org.mockito.Mockito.*;

class CommandManagerTest {

    private CommandManager commandManager;

    @BeforeEach
    void setUp() {
        // Reset dell'istanza singleton per ogni test (in pratica possibile solo se CommandManager ha un metodo per farlo)
        commandManager = CommandManager.getInstance();
    }

    @Test
    void testExecuteCommandExecutesAndStoresCommand() {
        Command cmd = mock(Command.class);

        commandManager.executeCommand(cmd);

        verify(cmd, times(1)).execute();
    }

    @Test
    void testUndoLastCommandCallsUndo() {
        Command cmd = mock(Command.class);

        commandManager.executeCommand(cmd);
        commandManager.undoLastCommand();

        verify(cmd, times(1)).undo();
    }

    @Test
    void testUndoOnEmptyHistoryDoesNothing() {
        // Nessuna eccezione deve essere lanciata
        commandManager.undoLastCommand();
    }
}
