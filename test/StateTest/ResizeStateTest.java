package StateTest;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.ResizeCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.ResizeState;
import projectworkgroup6.View.ShapeView;

import static org.mockito.Mockito.*;

class ResizeStateTest {

    private Shape shape;
    private ShapeView shapeView;
    private ResizeCommand resizeCommand;
    private ResizeState resizeState;
    private StateController controller;

    @BeforeEach
    void setUp() {
        shape = mock(Shape.class);
        shapeView = mock(ShapeView.class);
        when(shapeView.getShape()).thenReturn(shape);

        // dimensioni iniziali shape
        when(shape.getX()).thenReturn(100.0);
        when(shape.getY()).thenReturn(200.0);
        when(shape.getXc()).thenReturn(100.0);
        when(shape.getYc()).thenReturn(200.0);
        when(shape.getDim1()).thenReturn(100.0);
        when(shape.getDim2()).thenReturn(50.0);
        when(shape.getRotation()).thenReturn(0.0);

        resizeCommand = mock(ResizeCommand.class);
        resizeState = new ResizeState(shapeView);
        resizeState.setResizeCommand(resizeCommand);

        controller = mock(StateController.class);
        StateController.setInstance(controller);
        CommandManager.setInstance(mock(CommandManager.class));
    }

    @Test
    void testStartDraggingInitializesValues() {
        resizeState.startDragging(150, 250);
        // Non testabile direttamente senza getter, ma lanciare il metodo non deve causare errori
    }

    @Test
    void testHandleMouseDraggedResizesShape() {
        resizeState.startDragging(150, 250); // inizializza startX/Y

        resizeState.handleMouseDragged(180, 280); // simula trascinamento verso basso-destra

        verify(shape, atLeastOnce()).resize(anyDouble(), anyDouble(), anyDouble(), anyDouble());
        verify(resizeCommand, atLeastOnce()).accumulate(anyDouble(), anyDouble());
        verify(controller).notifyCanvasToRepaint();
    }

    @Test
    void testHandleMouseReleasedCommitsResizeCommand() {
        when(shape.getX()).thenReturn(100.0);
        when(shape.getY()).thenReturn(200.0);

        resizeState.handleMouseReleased(180, 280);

        verify(shape, atLeastOnce()).setX(anyDouble());
        verify(shape, atLeastOnce()).setY(anyDouble());
        verify(resizeCommand).undofactor(anyDouble(), anyDouble());
        verify(CommandManager.getInstance()).executeCommand(resizeCommand);
        verify(controller).notifyShapeSelected(shape);
    }
}
