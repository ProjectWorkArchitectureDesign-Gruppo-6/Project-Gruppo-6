package StateTest;

import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.MoveCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.GroupBorderDecorator;
import projectworkgroup6.Model.Group;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.MultipleSelectState;
import projectworkgroup6.State.TemporaryTranslationState;
import projectworkgroup6.View.ShapeView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class TemporaryTranslationStateTest {

    private Group group;
    private Shape groupShape;
    private GroupBorderDecorator decorator;
    private MoveCommand moveCommand;
    private StateController controller;
    private TemporaryTranslationState state;

    @BeforeEach
    void setUp() {
        group = mock(Group.class);
        groupShape = group;
        when(group.getShapes()).thenReturn(Arrays.asList(mock(Shape.class), mock(Shape.class)));

        decorator = mock(GroupBorderDecorator.class);
        when(decorator.getShape()).thenReturn(groupShape);

        controller = mock(StateController.class);
        StateController.setInstance(controller);

        moveCommand = mock(MoveCommand.class);
        CommandManager.setInstance(mock(CommandManager.class));

        state = new TemporaryTranslationState(decorator);
        state.setMoveCommand(moveCommand);
    }

    @Test
    void testStartDraggingInitializesValues() {
        List<ShapeView> views = Arrays.asList(mock(ShapeView.class), mock(ShapeView.class));
        state.startDragging(120, 130, views);
        // implicito: nessuna eccezione
    }

    @Test
    void testHandleMouseDraggedCallsMoveAndAccumulate() {
        Shape shape = mock(Shape.class);
        when(decorator.getShape()).thenReturn(shape);

        state.startDragging(100, 100, Collections.emptyList());
        state.handleMouseDragged(110, 120);

        verify(shape).move(10.0, 20.0);
        verify(moveCommand).accumulate(10.0, 20.0);
        verify(controller).notifyCanvasToRepaint();
    }

    @Test
    void testHandleMouseReleasedExecutesCommand() {
        state.handleMouseReleased(150, 160);

        verify(moveCommand).undo();
        verify(CommandManager.getInstance()).executeCommand(moveCommand);
        verify(controller).notifyShapeSelected(groupShape);
    }

    @Test
    void testHandleClickRestoresGroupAndChangesState() {
        List<Shape> shapes = Arrays.asList(mock(Shape.class), mock(Shape.class));
        List<ShapeView> views = Arrays.asList(mock(ShapeView.class), mock(ShapeView.class));
        when(group.getShapes()).thenReturn(shapes);
        state.startDragging(100, 100, views);

        MouseEvent clickEvent = mock(MouseEvent.class);
        state.handleClick(clickEvent, 110, 110, Collections.emptyMap());

        verify(controller).removeShape(group, decorator);
        for (int i = 0; i < shapes.size(); i++) {
            verify(controller).addShape(shapes.get(i), views.get(i));
        }
        verify(controller).addGroup(decorator);
        verify(controller).setState(MultipleSelectState.getInstance());
    }
}
