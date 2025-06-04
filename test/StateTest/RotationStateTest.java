package StateTest;

import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.RotateCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.RotationState;
import projectworkgroup6.State.SingleSelectState;
import projectworkgroup6.View.ShapeView;

import java.util.Collections;

import static org.mockito.Mockito.*;

class RotationStateTest {

    private Shape shape;
    private SelectedDecorator decorator;
    private RotateCommand rotateCommand;
    private RotationState state;
    private StateController controller;

    @BeforeEach
    void setUp() {
        shape = mock(Shape.class);
        when(shape.getXc()).thenReturn(100.0);
        when(shape.getYc()).thenReturn(200.0);
        when(shape.getDim1()).thenReturn(50.0);
        when(shape.getDim2()).thenReturn(50.0);
        when(shape.getRotation()).thenReturn(0.0);

        decorator = mock(SelectedDecorator.class);
        when(decorator.getShape()).thenReturn(shape);

        rotateCommand = mock(RotateCommand.class);

        controller = mock(StateController.class);
        StateController.setInstance(controller);
        CommandManager.setInstance(mock(CommandManager.class));

        state = new RotationState(decorator);
        state.setRotateCommand(rotateCommand);
    }

    @Test
    void testStartRotatingInitializesCorrectly() {
        state.startRotating(120.0, 220.0);
        // Non verificabile direttamente, ma non deve lanciare errori
    }

    @Test
    void testHandleMouseDraggedAppliesRotation() {
        state.startRotating(120.0, 220.0); // punto iniziale per startRotating

        state.handleMouseDragged(140.0, 240.0); // punto finale simulato

        verify(shape).setRotation(anyDouble());
        verify(rotateCommand).finalizeRotation();
        verify(controller).notifyCanvasToRepaint();
    }

    @Test
    void testHandleMouseReleasedExecutesCommandAndSwitchesState() {
        state.startRotating(120.0, 220.0); // simula che sia in fase di rotazione

        state.handleMouseReleased(140.0, 240.0);

        verify(CommandManager.getInstance()).executeCommand(rotateCommand);
        verify(controller).setState(SingleSelectState.getInstance());
    }

    @Test
    void testHandleClickSwitchesToSingleSelect() {
        MouseEvent event = mock(MouseEvent.class);

        state.handleClick(event, 100.0, 100.0, Collections.emptyMap());

        verify(controller).setState(SingleSelectState.getInstance());
    }
}
