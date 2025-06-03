package StateTest;

import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.DeleteCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.DeleteState;
import projectworkgroup6.View.ShapeView;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class DeleteStateTest {

    private DeleteState deleteState;
    private Map<Shape, ShapeView> shapeMap;

    @BeforeEach
    void setUp() {
        deleteState = new DeleteState();
        shapeMap = new HashMap<>();
    }

    @Test
    void testHandleClickExecutesDeleteCommand() {
        // Arrange
        Shape shape1 = mock(Shape.class);
        Shape shape2 = mock(Shape.class);
        ShapeView view1 = mock(ShapeView.class);
        ShapeView view2 = mock(ShapeView.class);

        when(shape1.contains(50, 50)).thenReturn(false);
        when(shape2.contains(50, 50)).thenReturn(true); // solo shape2 contiene il punto

        shapeMap.put(shape1, view1);
        shapeMap.put(shape2, view2);

        MouseEvent event = mock(MouseEvent.class);

        // Mock CommandManager
        CommandManager manager = mock(CommandManager.class);
        CommandManager.setInstance(manager);

        // Act
        deleteState.handleClick(event, 50, 50, shapeMap);

        // Assert
        verify(manager).executeCommand(any(DeleteCommand.class));
        verifyNoMoreInteractions(manager);
    }

    @Test
    void testRecoverShapesResetsAndRestoresEachShape() {
        // Arrange
        Shape shape = mock(Shape.class);
        ShapeView decoratedView = mock(ShapeView.class);
        ShapeView baseView = mock(ShapeView.class);
        when(decoratedView.undecorate()).thenReturn(baseView);

        shapeMap.put(shape, decoratedView);

        // Mock StateController
        StateController controller = mock(StateController.class);
        StateController.setInstance(controller);

        // Act
        deleteState.recoverShapes(shapeMap);

        // Assert: reset dello stato logico
        verify(shape).setSelected(false);
        verify(shape).setGroup(0);
        verify(shape).setEditing(false);

        // gestione della vista
        verify(controller).removeShape(shape, decoratedView);
        verify(controller).addShape(shape, baseView);
        verify(controller).notifyShapeDeselected();
    }
}
