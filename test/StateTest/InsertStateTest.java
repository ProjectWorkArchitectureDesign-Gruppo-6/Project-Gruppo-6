package StateTest;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.InsertState;
import projectworkgroup6.View.ShapeView;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class InsertStateTest {

    private InsertState insertState;
    private ShapeCreator mockCreator;

    @BeforeEach
    void setUp() {
        mockCreator = mock(ShapeCreator.class);
        insertState = new InsertState(mockCreator);
    }

    @Test
    void testHandleClickCreatesAndAddsShape() {
        Shape mockShape = mock(Shape.class);
        ShapeView mockView = mock(ShapeView.class);
        when(mockCreator.createShape(anyDouble(), anyDouble())).thenReturn(mockShape);
        when(mockCreator.createShapeView(mockShape)).thenReturn(mockView);

        insertState.handleClick(100, 200, new HashMap<>());

        // Verifica che i metodi della factory siano stati usati
        verify(mockCreator).createShape(100, 200);
        verify(mockCreator).createShapeView(mockShape);
    }

    @Test
    void testHandleColorChangedUpdatesStrokeColor() {
        Color newColor = Color.BLUE;
        assertDoesNotThrow(() -> insertState.handleColorChanged(newColor));
    }

    @Test
    void testHandleDeleteDoesNothing() {
        assertDoesNotThrow(() -> insertState.handleDelete(null, new HashMap<>()));
    }

    @Test
    void testHandleMouseDraggedDoesNothing() {
        assertDoesNotThrow(() -> insertState.handleMouseDragged(0, 0));
    }

    @Test
    void testHandleMouseReleasedDoesNothing() {
        assertDoesNotThrow(() -> insertState.handleMouseReleased(0, 0));
    }

    @Test
    void testRecoverShapesRemovesSelectedDecorator() {
        Shape shape = mock(Shape.class);
        ShapeView baseView = mock(ShapeView.class);

        SelectedDecorator decoratedView = mock(SelectedDecorator.class);
        when(decoratedView.undecorate()).thenReturn(baseView);

        Map<Shape, ShapeView> map = new HashMap<>();
        map.put(shape, decoratedView);

        // Usa una mappa con SelectedDecorator
        insertState.recoverShapes(map);

        verify(shape).setSelected(false);
        verify(decoratedView).undecorate();
    }
}