package StateTest;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Group;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.MultipleSelectState;
import projectworkgroup6.View.ShapeView;

import java.util.*;

import static org.mockito.Mockito.*;

class MultipleSelectStateTest {

    private StateController controller;
    private CommandManager commandManager;
    private Map<Shape, ShapeView> map;
    private Shape shape;
    private ShapeView view;

    @BeforeEach
    void setUp() {
        controller = mock(StateController.class);
        StateController.setInstance(controller);

        commandManager = mock(CommandManager.class);
        CommandManager.setInstance(commandManager);

        shape = mock(Shape.class);
        view = mock(ShapeView.class);
        when(view.getShape()).thenReturn(shape);

        map = new HashMap<>();
        map.put(shape, view);

        when(controller.getCurrentGroup()).thenReturn(0);
    }

    @Test
    void testSelectShapeOnClick() {
        MouseEvent event = mock(MouseEvent.class);
        when(event.getButton()).thenReturn(MouseButton.PRIMARY);
        when(shape.contains(anyDouble(), anyDouble())).thenReturn(true);
        when(shape.isSelected()).thenReturn(false);

        MultipleSelectState state = MultipleSelectState.getInstance();
        state.handleClick(event, 50, 50, map);

        verify(shape).setSelected(true);
        verify(shape).setGroup(1);
        verify(controller).addShape(eq(shape), any());
    }

    @Test
    void testDeselectShapeOnClick() {
        MouseEvent event = mock(MouseEvent.class);
        when(event.getButton()).thenReturn(MouseButton.PRIMARY);
        when(shape.contains(anyDouble(), anyDouble())).thenReturn(true);
        when(shape.isSelected()).thenReturn(true);

        MultipleSelectState state = MultipleSelectState.getInstance();
        state.handleClick(event, 50, 50, map);

        verify(shape).setSelected(false);
        verify(shape).setGroup(0);
        verify(controller).addShape(eq(shape), any());
    }

    @Test
    void testRecoverShapesUndecoratesOutsideGroup() {
        when(view.undecorate()).thenReturn(view);

        MultipleSelectState.getInstance().setGroup(null); // simuliamo gruppo non attivo

        MultipleSelectState.getInstance().recoverShapes(map);

        verify(shape).setSelected(false);
        verify(shape).setGroup(0);
        verify(controller).removeShape(shape, view);
        verify(controller).addShape(shape, view);
    }

    @Test
    void testHandleDeleteExecutesDeleteCommand() {
        Group group = mock(Group.class);
        when(group.getShapes()).thenReturn(Arrays.asList(shape));
        MultipleSelectState.getInstance().setGroup(group);

        KeyEvent deleteEvent = mock(KeyEvent.class);
        when(deleteEvent.getCode()).thenReturn(KeyCode.DELETE);

        MultipleSelectState.getInstance().handleDelete(deleteEvent, map);

        verify(commandManager).executeCommand(any());
    }

    @Test
    void testHandleColorChangedAppliesFill() {
        Group group = mock(Group.class);
        when(group.getBorder()).thenReturn(new projectworkgroup6.Model.ColorModel(1, 1, 1, 1));
        when(group.getShapes()).thenReturn(Arrays.asList(shape));

        ShapeView baseView = mock(ShapeView.class);
        when(view.undecorate()).thenReturn(baseView);

        when(controller.getMap()).thenReturn(map);

        MultipleSelectState.getInstance().setGroup(group);
        MultipleSelectState.getInstance().handleColorChanged(Color.BLACK, Color.RED);

        verify(commandManager).executeCommand(any());
        verify(controller).addShape(eq(shape), any());
    }
}

