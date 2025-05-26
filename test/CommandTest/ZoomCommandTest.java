package CommandTest;

import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import projectworkgroup6.Command.ZoomCommand;

public class ZoomCommandTest {

    private Node mockNode;
    private ZoomCommand zoomCommand;

    @BeforeEach
    public void setUp() {
        mockNode = mock(Node.class);
        // Imposta i valori iniziali di scala simulati
        when(mockNode.getScaleX()).thenReturn(2.0);
        when(mockNode.getScaleY()).thenReturn(3.0);

        zoomCommand = new ZoomCommand(mockNode, 1.5);
    }

    @Test
    public void testExecuteAndUndo() {
        // Esegue lo zoom
        zoomCommand.execute();

        // Verifica che la scala sia stata moltiplicata per il fattore
        verify(mockNode).setScaleX(2.0 * 1.5);
        verify(mockNode).setScaleY(3.0 * 1.5);

        // Aggiorna i valori simulati per riflettere lo stato dopo execute
        when(mockNode.getScaleX()).thenReturn(3.0);
        when(mockNode.getScaleY()).thenReturn(4.5);

        // Esegue l'annullamento dello zoom
        zoomCommand.undo();

        // Verifica che la scala sia stata riportata al valore originale
        verify(mockNode).setScaleX(3.0 / 1.5);
        verify(mockNode).setScaleY(4.5 / 1.5);
    }
}