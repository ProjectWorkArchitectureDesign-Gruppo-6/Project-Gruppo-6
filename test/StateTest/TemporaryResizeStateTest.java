package StateTest;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.ResizeCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.GroupBorderDecorator;
import projectworkgroup6.Model.Group;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.MultipleSelectState;
import projectworkgroup6.State.TemporaryResizeState;
import projectworkgroup6.View.ShapeView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class TemporaryResizeStateTest {

    private Shape groupShape;
    private GroupBorderDecorator decorator;
    private ResizeCommand resizeCommand;
    private TemporaryResizeState state;
    private StateController controller;
    private CommandManager commandManager;
    private Group group;

    @BeforeEach
    void setUp() {
        group = mock(Group.class);
        groupShape = group;
        when(group.getX()).thenReturn(100.0);
        when(group.getY()).thenReturn(100.0);
        when(group.getXc()).thenReturn(100.0);
        when(group.getYc()).thenReturn(100.0);
        when(group.getDim1()).thenReturn(100.0);
        when(group.getDim2()).thenReturn(50.0);
        when(group.getRotation()).thenReturn(0.0);
        when(group.getShapes()).thenReturn(Arrays.asList(mock(Shape.class), mock(Shape.class)));

        decorator = mock(GroupBorderDecorator.class);
        when(decorator.getShape()).thenReturn(groupShape);

        resizeCommand = mock(ResizeCommand.class);

        controller = mock(StateController.class);
        StateController.setInstance(controller);

        commandManager = mock(CommandManager.class);
        CommandManager.setInstance(commandManager);

        state = new TemporaryResizeState(decorator);
        state.setResizeCommand(resizeCommand);
    }

    @Test
    void testStartDraggingInitializes() {
        List<ShapeView> views = Arrays.asList(mock(ShapeView.class), mock(ShapeView.class));
        state.startDragging(150, 150, views);
        // testato implicitamente nel drag
    }

    @Test
    void testHandleMouseDraggedAppliesResize() {
        List<ShapeView> views = Arrays.asList(mock(ShapeView.class), mock(ShapeView.class));
        state.startDragging(120, 120, views);
        state.handleMouseDragged(140, 140); // simulate drag toward bottom-right

        verify(groupShape, atLeastOnce()).resize(anyDouble(), anyDouble(), anyDouble(), anyDouble());
        verify(resizeCommand, atLeastOnce()).accumulate(anyDouble(), anyDouble());
        verify(controller).notifyCanvasToRepaint();
    }

    @Test
    void testHandleMouseReleasedExecutesCommand() {
        state.handleMouseReleased(200, 200);

        verify(groupShape, atLeastOnce()).setX(anyDouble());
        verify(groupShape, atLeastOnce()).setY(anyDouble());
        verify(resizeCommand).undofactor(anyDouble(), anyDouble());
        verify(commandManager).executeCommand(resizeCommand);
        verify(controller).notifyShapeSelected(groupShape);
    }

    @Test
    void testHandleClickRestoresGroupAndSwitchesState() {
        List<Shape> shapes = Arrays.asList(mock(Shape.class), mock(Shape.class));
        List<ShapeView> views = Arrays.asList(mock(ShapeView.class), mock(ShapeView.class));
        when(group.getShapes()).thenReturn(shapes);
        state.startDragging(150, 150, views);

        state.handleClick(mock(MouseEvent.class), 160, 160, Collections.emptyMap());

        for (int i = 0; i < shapes.size(); i++) {
            verify(controller).addShape(eq(shapes.get(i)), eq(views.get(i)));
        }

        verify(controller, atLeastOnce()).addGroup(decorator);
        verify(controller).setState(MultipleSelectState.getInstance());
    }
}
