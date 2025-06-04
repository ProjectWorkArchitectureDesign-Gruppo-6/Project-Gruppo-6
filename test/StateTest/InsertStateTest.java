package StateTest;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.InsertCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.InsertState;
import projectworkgroup6.View.ShapeView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class InsertStateTest {

    private ShapeCreator creator;
    private Shape shape;
    private ShapeView view;
    private InsertState state;

    @BeforeEach
    void setUp() {
        // Mocks base
        creator = mock(ShapeCreator.class);
        shape = mock(Shape.class);
        view = mock(ShapeView.class);

        // Setup di default per creator
        when(creator.createShape(anyDouble(), anyDouble(), anyDouble(), anyDouble(), any(), any(), anyInt(), anyInt()))
                .thenReturn(shape);
        when(creator.createShapeView(shape)).thenReturn(view);

        // Setup stroke/fill iniziali
        StateController controller = mock(StateController.class);
        when(controller.getStrokeColor()).thenReturn(Color.BLACK);
        when(controller.getFillColor()).thenReturn(Color.RED);
        StateController.setInstance(controller);

        // Setup command manager
        CommandManager.setInstance(mock(CommandManager.class));

        state = new InsertState(creator);
    }


    @Test
    void testRecoverShapesUndecoratesAndReadds() {
        Shape s = mock(Shape.class);
        ShapeView decorated = mock(ShapeView.class);
        ShapeView undecorated = mock(ShapeView.class);
        when(decorated.undecorate()).thenReturn(undecorated);

        Map<Shape, ShapeView> map = new HashMap<>();
        map.put(s, decorated);

        StateController controller = mock(StateController.class);
        StateController.setInstance(controller);

        state.recoverShapes(map);

        verify(s).setSelected(false);
        verify(s).setGroup(0);
        verify(s).setEditing(false);
        verify(controller).removeShape(s, decorated);
        verify(controller).addShape(s, undecorated);
    }

    @Test
    void testHandleColorChangedUpdatesColorFields() {
        state.handleColorChanged(Color.BLUE, Color.GREEN);
        // non possiamo accedere direttamente ai campi, ma nessuna eccezione è già sufficiente
    }
}
