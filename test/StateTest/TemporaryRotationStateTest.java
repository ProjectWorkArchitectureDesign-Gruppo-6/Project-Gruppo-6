package StateTest;

import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.RotateCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.GroupBorderDecorator;
import projectworkgroup6.Model.Group;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.MultipleSelectState;
import projectworkgroup6.State.TemporaryRotationState;
import projectworkgroup6.View.ShapeView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class TemporaryRotationStateTest {

    private Group group;
    private Shape groupShape;
    private GroupBorderDecorator decorator;
    private RotateCommand rotateCommand;
    private StateController controller;
    private TemporaryRotationState state;

    @BeforeEach
    void setUp() {
        group = mock(Group.class);
        groupShape = group;
        when(group.getXc()).thenReturn(100.0);
        when(group.getYc()).thenReturn(100.0);
        when(group.getDim1()).thenReturn(100.0);
        when(group.getDim2()).thenReturn(50.0);
        when(group.getRotation()).thenReturn(0.0);
        when(group.getShapes()).thenReturn(Arrays.asList(mock(Shape.class), mock(Shape.class)));

        decorator = mock(GroupBorderDecorator.class);
        when(decorator.getShape()).thenReturn(groupShape);

        rotateCommand = mock(RotateCommand.class);

        controller = mock(StateController.class);
        StateController.setInstance(controller);
        CommandManager.setInstance(mock(CommandManager.class));

        state = new TemporaryRotationState(decorator);
        state.setRotateCommand(rotateCommand);
    }

    @Test
    void testStartRotatingInitializesState() {
        List<ShapeView> views = Arrays.asList(mock(ShapeView.class), mock(ShapeView.class));
        state.startRotating(120.0, 120.0, views);
        // effetto verificato implicitamente in drag
    }

    @Test
    void testHandleMouseDraggedAppliesRotation() {
        List<ShapeView> views = Arrays.asList(mock(ShapeView.class), mock(ShapeView.class));
        state.startRotating(120.0, 120.0, views);

        state.handleMouseDragged(150.0, 150.0);

        verify(groupShape).setRotation(anyDouble());
        verify(rotateCommand).finalizeRotation();
        verify(controller).notifyCanvasToRepaint();
    }

    @Test
    void testHandleMouseReleasedExecutesRotateCommand() {
        List<ShapeView> views = Arrays.asList(mock(ShapeView.class), mock(ShapeView.class));
        state.startRotating(120.0, 120.0, views);

        state.handleMouseReleased(150.0, 150.0);

        verify(CommandManager.getInstance()).executeCommand(rotateCommand);
        verify(controller).notifyShapeSelected(groupShape);
    }

    @Test
    void testHandleClickRestoresGroupAndSwitchesState() {
        List<Shape> shapes = Arrays.asList(mock(Shape.class), mock(Shape.class));
        List<ShapeView> views = Arrays.asList(mock(ShapeView.class), mock(ShapeView.class));
        when(group.getShapes()).thenReturn(shapes);
        state.startRotating(120.0, 120.0, views);

        state.handleClick(mock(MouseEvent.class), 130.0, 130.0, Collections.emptyMap());

        for (int i = 0; i < shapes.size(); i++) {
            verify(controller).addShape(eq(shapes.get(i)), eq(views.get(i)));
        }

        verify(controller).addGroup(decorator);
        verify(controller).setState(MultipleSelectState.getInstance());
    }
}
