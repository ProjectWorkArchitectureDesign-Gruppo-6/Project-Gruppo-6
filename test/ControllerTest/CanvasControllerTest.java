package ControllerTest;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Controller.CanvasController;
import projectworkgroup6.Controller.MainController;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.CanvasState;
import projectworkgroup6.View.CanvasView;
import projectworkgroup6.View.ShapeView;

import java.util.*;

import static org.mockito.Mockito.*;

public class CanvasControllerTest {

    private CanvasController canvasController;
    private CanvasView mockCanvasView;
    private CanvasState mockCanvasState;
    private Map<Shape, ShapeView> testMap;

    @BeforeEach
    void setUp() {
        canvasController = new CanvasController();
        mockCanvasView = mock(CanvasView.class);
        mockCanvasState = mock(CanvasState.class);
        canvasController.setCanvasView(mockCanvasView);
        canvasController.onStateChanged(mockCanvasState);

        // Mappa di esempio
        Shape shape = mock(Shape.class);
        ShapeView shapeView = mock(ShapeView.class);
        testMap = new HashMap<>();
        testMap.put(shape, shapeView);
    }
    //il controller aggiorna il proprio stato interno e chiama il metodo render()
// del CanvasView con l’elenco delle ShapeView.
    @Test
    void testOnCanvasChangedCallsRender() {
        canvasController.onCanvasChanged(testMap);
        verify(mockCanvasView, times(1)).render(testMap.values());
    }

    // Controllo che Il nuovo stato vine memorizzato nel controller e
    // che venga invocato il metodo recoverShapes() passando la mappa attuale delle shap
    @Test
    void testOnStateChangedStoresStateAndCallsRecoverShapes() {
        canvasController.onCanvasChanged(testMap);
        CanvasState newState = mock(CanvasState.class);
        canvasController.onStateChanged(newState);
        verify(newState).recoverShapes(testMap);
    }

    //Controlla che quando cambia il colore (ColorModel notifica un cambiamento),
    // il controller inoltri il nuovo colore allo stato corrente tramite handleColorChanged().
    @Test
    void testOnColorChangedCallsHandleColorChanged() {
        Color color = Color.BLUE;
        canvasController.onColorChanged(color);
        verify(mockCanvasState).handleColorChanged(color);
    }
    //Verifica che quando viene premuto un tasto (es. DELETE),
    //il controller passi l’evento e la mappa corrente allo stato, invocando handleDelete().
    @Test
    void testHandleDeleteDelegatesToState() {
        // Usa un'istanza reale di KeyEvent (no mock)
        KeyEvent deleteEvent = new KeyEvent(
                KeyEvent.KEY_PRESSED,
                "",
                "",
                KeyCode.DELETE,
                false,
                false,
                false,
                false
        );

        canvasController.onCanvasChanged(testMap);
        canvasController.handleDelete(deleteEvent);
        verify(mockCanvasState).handleDelete(deleteEvent, testMap);
    }

    //Controlla che il controller delega correttamente la gestione del rilascio del mouse allo stato attuale.
    @Test
    void testHandleMouseReleasedDelegatesToState() {
        canvasController.handleMouseReleased(100, 200);
        verify(mockCanvasState).handleMouseReleased(100, 200);
    }
    //Verifica che il trascinamento del mouse (drag) venga passato allo stato corrente.
    @Test
    void testHandleMouseDraggedDelegatesToState() {
        canvasController.handleMouseDragged(50, 75);
        verify(mockCanvasState).handleMouseDragged(50, 75);
    }
    //Controlla che un click su uno strumento di movimento (es. selezione o traslazione)
// venga inoltrato al metodo handleMoveClick() dello stato.
    @Test
    void testHandleMoveClickDelegatesToState() {
        canvasController.handleMoveClick(20, 30);
        verify(mockCanvasState).handleMoveClick(20, 30);
    }

    /*Assicura che un click diretto sul canvas venga gestito dallo stato attuale tramite handleClick(),
     con le coordinate e la mappa corrente.*/
    @Test
    void testHandleCanvasClickDelegatesToState() {
        canvasController.onCanvasChanged(testMap);
        canvasController.handleCanvasClick(10, 15);
        verify(mockCanvasState).handleClick(10, 15, testMap);
    }
}