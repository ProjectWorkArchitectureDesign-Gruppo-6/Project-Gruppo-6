package StateTest;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.*;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Model.TextBox;
import projectworkgroup6.State.SingleSelectState;
import projectworkgroup6.View.ShapeView;

import java.util.*;

import static org.mockito.Mockito.*;

class SingleSelectStateTest {

    private Shape shape;
    private ShapeView shapeView;
    private StateController controller;
    private Map<Shape, ShapeView> map;

    @BeforeEach
    void setUp() {
        shape = mock(TextBox.class);
        shapeView = mock(ShapeView.class);
        when(shapeView.getShape()).thenReturn(shape);
        when(shape.getXc()).thenReturn(100.0);
        when(shape.getYc()).thenReturn(200.0);
        when(shape.getDim1()).thenReturn(50.0);
        when(shape.getDim2()).thenReturn(50.0);
        when(shape.getRotation()).thenReturn(0.0);
        when(shape.contains(anyDouble(), anyDouble())).thenReturn(true);

        controller = mock(StateController.class);
        StateController.setInstance(controller);
        CommandManager.setInstance(mock(CommandManager.class));

        map = new HashMap<>();
        map.put(shape, shapeView);
    }

    @Test
    void testSelectShapeOnClick() {
        MouseEvent event = mock(MouseEvent.class);
        when(event.getButton()).thenReturn(MouseButton.PRIMARY);
        when(shape.isSelected()).thenReturn(false);

        SingleSelectState.getInstance().handleClick(event, 110, 210, map);

        verify(shape).setSelected(true);
        verify(controller).addShape(eq(shape), any(SelectedDecorator.class));
        verify(controller).notifyShapeSelected(shape);
    }

    @Test
    void testDeselectShapeOnClick() {
        MouseEvent event = mock(MouseEvent.class);
        when(event.getButton()).thenReturn(MouseButton.PRIMARY);
        when(shape.isSelected()).thenReturn(true);

        SelectedDecorator selected = mock(SelectedDecorator.class);
        when(selected.getShape()).thenReturn(shape);

        SingleSelectState.getInstance().handleClick(event, 110, 210, map);
        // chiamata successiva simula clic su shape già selezionata
        SingleSelectState.getInstance().handleClick(event, 110, 210, map);

        verify(shape, atLeastOnce()).setSelected(false);
        verify(controller, atLeastOnce()).removeShape(eq(shape), any());
    }

    @Test
    void testHandleDeleteExecutesDeleteCommand() {
        SelectedDecorator selected = mock(SelectedDecorator.class);
        when(selected.getShape()).thenReturn(shape);

        SingleSelectState.getInstance().handleClick(mock(MouseEvent.class), 110, 210, map);

        KeyEvent keyEvent = mock(KeyEvent.class);
        when(keyEvent.getCode()).thenReturn(KeyCode.DELETE);

        SingleSelectState.getInstance().handleDelete(keyEvent, map);

        verify(CommandManager.getInstance()).executeCommand(any(DeleteCommand.class));
    }

    @Test
    void testHandleColorChangedExecutesCorrectCommand() {
        SelectedDecorator selected = mock(SelectedDecorator.class);
        when(selected.getShape()).thenReturn(shape);
        when(shape.getBorder()).thenReturn(ColorModel.fromColor(Color.BLACK));

        SingleSelectState.getInstance().handleClick(mock(MouseEvent.class), 110, 210, map);

        // Simula rimozione decorazione tripla
        ShapeView base = mock(ShapeView.class);
        when(shapeView.undecorate()).thenReturn(base);
        when(base.undecorate()).thenReturn(base);
        when(base.undecorate()).thenReturn(base);

        when(shape.isSelected()).thenReturn(true);

        SingleSelectState.getInstance().handleColorChanged(Color.BLACK, Color.RED);

        verify(CommandManager.getInstance()).executeCommand(any(ChangeFillCommand.class));
        verify(controller).addShape(eq(shape), any());
    }

    @Test
    void testHandleChangeFontColorExecutesCommand() {
        when(shape.isSelected()).thenReturn(true);

        SelectedDecorator selected = mock(SelectedDecorator.class);
        when(selected.getShape()).thenReturn(shape);

        SingleSelectState.getInstance().handleClick(mock(MouseEvent.class), 110, 210, map);
        SingleSelectState.getInstance().handleChangeFontColor(Color.BLUE);

        verify(CommandManager.getInstance()).executeCommand(any(ChangeFontColorCommand.class));
    }

    @Test
    void testHandleChangeFontNameExecutesCommand() {
        when(shape.isSelected()).thenReturn(true);

        SelectedDecorator selected = mock(SelectedDecorator.class);
        when(selected.getShape()).thenReturn(shape);

        SingleSelectState.getInstance().handleClick(mock(MouseEvent.class), 110, 210, map);
        SingleSelectState.getInstance().handleChangeFontName("Verdana");

        verify(CommandManager.getInstance()).executeCommand(any(ChangeFontNameCommand.class));
    }

    @Test
    void testHandleChangeFontSizeExecutesCommand() {
        when(shape.isSelected()).thenReturn(true);

        SelectedDecorator selected = mock(SelectedDecorator.class);
        when(selected.getShape()).thenReturn(shape);

        SingleSelectState.getInstance().handleClick(mock(MouseEvent.class), 110, 210, map);
        SingleSelectState.getInstance().handleChangeFontSize(20);

        verify(CommandManager.getInstance()).executeCommand(any(ChangeFontSizeCommand.class));
    }
}
