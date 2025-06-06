package StateTest;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.InsertCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.Factory.PolygonCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.InsertPolygonState;
import projectworkgroup6.View.ShapeView;

import java.util.*;

import static org.mockito.Mockito.*;

class InsertPolygonStateTest {

    private PolygonCreator creator;
    private InsertPolygonState state;
    private Map<Shape, ShapeView> map;

    @BeforeEach
    void setUp() {
        creator = mock(PolygonCreator.class);
        when(creator.getTempVertices()).thenReturn(new ArrayList<>());

        map = new HashMap<>();

        // Mock colori iniziali
        StateController stateController = mock(StateController.class);
        when(stateController.getStrokeColor()).thenReturn(Color.BLACK);
        when(stateController.getFillColor()).thenReturn(Color.RED);
        StateController.setInstance(stateController);

        // Mock command manager
        CommandManager.setInstance(mock(CommandManager.class));

        state = new InsertPolygonState(creator);
    }


    @Test
    void testRecoverShapesUndecoratesAndReadds() {
        Shape shape = mock(Shape.class);
        ShapeView decorated = mock(ShapeView.class);
        ShapeView undecorated = mock(ShapeView.class);
        when(decorated.undecorate()).thenReturn(undecorated);

        map.put(shape, decorated);

        StateController controller = mock(StateController.class);
        StateController.setInstance(controller);

        state.recoverShapes(map);

        verify(shape).setSelected(false);
        verify(shape).setEditing(false);
        verify(shape).setGroup(0);
        verify(controller).removeShape(shape, decorated);
        verify(controller).addShape(shape, undecorated);
    }

    @Test
    void testHandleColorChangedUpdatesColors() {
        Color newStroke = Color.GREEN;
        Color newFill = Color.BLUE;

        state.handleColorChanged(newStroke, newFill);

        // Non ci sono effetti verificabili pubblicamente, ma non deve lanciare eccezioni
    }
}


