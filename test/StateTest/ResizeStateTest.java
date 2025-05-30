package StateTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.ResizeCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;
import projectworkgroup6.State.ResizeState;
import projectworkgroup6.State.SingleSelectState;

import java.util.Map;

public class ResizeStateTest {

    private SelectedDecorator mockShapeView;
    private Shape mockShape;
    private ResizeCommand mockResizeCommand;
    private ResizeState resizeState;
    private StateController controllerMock;
    private CommandManager commandManagerMock;

    @BeforeEach
    void setUp() {
        // Mock Shape and its properties
        mockShape = mock(Shape.class);
        when(mockShape.getX()).thenReturn(50.0);
        when(mockShape.getY()).thenReturn(50.0);
        when(mockShape.getDim1()).thenReturn(20.0);
        when(mockShape.getDim2()).thenReturn(30.0);

        // Mock ShapeView decorator
        mockShapeView = mock(SelectedDecorator.class);
        when(mockShapeView.getShape()).thenReturn(mockShape);

        // Mock command and assign to state
        mockResizeCommand = mock(ResizeCommand.class);
        resizeState = new ResizeState(mockShapeView);
        resizeState.setResizeCommand(mockResizeCommand);

        // Mock and set singleton StateController
        controllerMock = mock(StateController.class);
        StateController.setInstance(controllerMock);

        // Mock and set singleton CommandManager
        commandManagerMock = mock(CommandManager.class);
        CommandManager.setInstance(commandManagerMock);
    }

    @Test
    void testHandleMouseDragged_ScaleUp() {
        // Simula partenza da (60, 60)
        resizeState.startDragging(60, 60);

        // Trascina a (70, 70) → aumento distanza dal centro
        double newX = 70;
        double newY = 70;

        resizeState.handleMouseDragged(newX, newY);

        // Verifica che siano chiamati i metodi corretti
        verify(controllerMock).removeShape(mockShape, mockShapeView);
        verify(controllerMock).addShape(mockShape, mockShapeView);
        verify(mockShape).resize(anyDouble());
        verify(mockResizeCommand).accumulate(anyDouble());
    }

    @Test
    void testHandleMouseReleased_ExecutesCommandAndNotifiesSelection() {
        // Act
        resizeState.handleMouseReleased(0, 0);

        // Assert
        verify(mockResizeCommand).undofactor();  // corretta verifica
        verify(commandManagerMock).executeCommand(mockResizeCommand);  // corretta verifica
        verify(controllerMock).notifyShapeSelected(mockShape);  // corrisponde al comportamento reale
    }
}