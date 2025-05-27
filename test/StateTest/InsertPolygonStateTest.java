package StateTest;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.InsertCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Polygon;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.InsertPolygonState;
import projectworkgroup6.View.ShapeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InsertPolygonStateTest {

    private InsertPolygonState state;
    private ShapeCreator creatorMock;
    private CommandManager commandManagerMock;
    private ShapeView shapeViewMock;
    private Shape shapeMock;
    private Map<Shape, ShapeView> map;

    @BeforeEach
    void setUp() {
        creatorMock = mock(ShapeCreator.class);
        shapeMock = mock(Polygon.class);
        shapeViewMock = mock(ShapeView.class);

        // Mock singleton CommandManager e StateController
        commandManagerMock = mock(CommandManager.class);
        StateController controllerMock = mock(StateController.class);

        when(controllerMock.getStrokeColor()).thenReturn(Color.BLACK);
        when(controllerMock.getFillColor()).thenReturn(Color.WHITE);

        state = new InsertPolygonState(creatorMock);
        map = new HashMap<>();
    }

    @Test
    void testHandleClick_AddsVertexAndCreatesPreviewPolygon() {
        ArrayList<double[]> vertices = new ArrayList<>();
        when(creatorMock.getTempVertices()).thenReturn(vertices);

        state.handleClick(10, 20, map);

        verify(creatorMock).addVertex(10, 20);
    }

    @Test
    void testHandleClick_DoubleClickCreatesPolygon() {
        ArrayList<double[]> vertices = new ArrayList<>();
        vertices.add(new double[]{0, 0});
        vertices.add(new double[]{1, 0});
        vertices.add(new double[]{1, 1});

        when(creatorMock.getTempVertices()).thenReturn(vertices);
        when(creatorMock.createShape(anyDouble(), anyDouble(), any(), any())).thenReturn(shapeMock);
        when(creatorMock.createShapeView(shapeMock)).thenReturn(shapeViewMock);

        // Primo click
        state.handleClick(10, 20, map);

        // Simula click entro soglia tempo doppio click
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}

        // Secondo click (doppio)
        state.handleClick(10, 20, map);

        verify(creatorMock).resetVertices();
        verify(commandManagerMock).executeCommand(any(InsertCommand.class));
    }

    @Test
    void testHandleColorChanged() {
        state.handleColorChanged(Color.RED, Color.BLUE);
        // Non ci sono assert qui perché setta solo i campi interni (no getter disponibili)
        // Si testa con gli effetti collaterali nei click (verificabili nei test sopra)
    }

    @Test
    void testRecoverShapes_RemovesAndAddsUndecorated() {
        Shape shape = mock(Shape.class);
        ShapeView decorated = mock(SelectedDecorator.class);
        ShapeView undecorated = mock(ShapeView.class);
        when(((SelectedDecorator) decorated).undecorate()).thenReturn(undecorated);

        StateController controller = mock(StateController.class);

        map.put(shape, decorated);
        state.recoverShapes(map);

        verify(shape).setSelected(false);
        verify(controller).removeShape(shape, decorated);
        verify(controller).addShape(shape, undecorated);
    }

    @Test
    void testHandleDeleteAndKeyTyped_NoOp() {
        // Coverage only – methods are empty
        state.handleDelete(null, map);
        state.handleKeyTyped(null, map);
        // No exception = pass
    }
}

