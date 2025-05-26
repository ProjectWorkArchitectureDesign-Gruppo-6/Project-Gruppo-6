package StateTest;

import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Model.Polygon;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.InsertPolygonState;
import projectworkgroup6.View.ShapeView;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InsertPolygonStateTest {

    private InsertPolygonState state;
    private ShapeCreator creator;
    private Map<Shape, ShapeView> map;

    @BeforeEach
    public void setUp() {
        creator = mock(ShapeCreator.class);
        state = new InsertPolygonState(creator);
        map = new HashMap<>();
        CommandManager.getInstance().reset(); // se c'è un singleton CommandManager
    }

    @Test
    public void testHandleClick_AddsVertexAndPreview() {
        when(creator.getTempVertices()).thenReturn(new ArrayList<>());

        state.handleClick(100, 200, map);

        verify(creator).addVertex(100, 200);
        // Preview shape should be created internally (not accessible, so just verify interaction)
    }

    @Test
    public void testHandleClick_DoubleClickCreatesPolygon() throws InterruptedException {
        List<double[]> vertices = new ArrayList<>();
        vertices.add(new double[]{0, 0});
        vertices.add(new double[]{100, 0});
        vertices.add(new double[]{50, 50});

        when(creator.getTempVertices()).thenReturn(vertices);
        when(creator.createShape(anyDouble(), anyDouble(), any(), any())).thenReturn(
                new Polygon((ArrayList<double[]>) vertices, false, null, null)
        );
        ShapeView viewMock = mock(ShapeView.class);
        when(creator.createShapeView(any())).thenReturn(viewMock);

        // First click: add vertex
        state.handleClick(10, 10, map);
        Thread.sleep(100); // Simula tempo tra click

        // Second click (double click)
        state.handleClick(10, 10, map);

        // Verifica che resetti i vertici e aggiunga la shape
        verify(creator).resetVertices();
        assertTrue(map.isEmpty(), "La mappa non dovrebbe essere modificata direttamente");
    }

    @Test
    public void testRecoverShapes_DeselezionaEUndecora() {
        Shape shape = mock(Shape.class);
        ShapeView undecorated = mock(ShapeView.class);
        SelectedDecorator decorated = mock(SelectedDecorator.class);

        when(decorated.undecorate()).thenReturn(undecorated);
        map.put(shape, decorated);

        StateController stateController = StateController.getInstance();
        stateController.addShape(shape, decorated); // Setup iniziale

        state.recoverShapes(map);

        // Verifica che il decoratore sia stato rimosso e sostituito
        assertEquals(undecorated, stateController.getMap().get(shape));
        verify(shape).setSelected(false);
    }

    @Test
    public void testHandleColorChanged() {
        Color red = Color.RED;
        state.handleColorChanged(red);
        // L'effetto sarà visibile solo nel click successivo: quindi nessuna assert diretta
    }

    @Test
    public void testHandleMouseDraggedAndReleased_DoNothing() {
        // Metodo vuoto → solo verifica che non lanci eccezioni
        assertDoesNotThrow(() -> {
            state.handleMouseDragged(50, 50);
            state.handleMouseReleased(50, 50);
        });
    }

    @Test
    public void testHandleDelete_DoesNothing() {
        KeyEvent event = mock(KeyEvent.class);
        assertDoesNotThrow(() -> state.handleDelete(event, map));
    }
}
