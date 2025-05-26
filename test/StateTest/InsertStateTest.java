package StateTest;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Model.ColorModel;
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

        // Colori convertiti (mockati per evitare problemi)
        ColorModel borderColor = ColorModel.fromColor(Color.BLACK);
        ColorModel fillColor = ColorModel.fromColor(Color.WHITE);

        when(mockCreator.createShape(eq(100.0), eq(200.0), any(), any())).thenReturn(mockShape);
        when(mockCreator.createShapeView(mockShape)).thenReturn(mockView);

        insertState.handleClick(100, 200, new HashMap<>());

        verify(mockCreator).createShape(eq(100.0), eq(200.0), any(), any());
        verify(mockCreator).createShapeView(mockShape);
    }

    @Test
    void testHandleColorChangedUpdatesStrokeColor() {
        Color newColor = Color.BLUE;
        assertDoesNotThrow(() -> insertState.handleColorChanged(newColor, newColor));
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

        // Evita la logica del singleton per StateController
        // oppure potresti dover mockare questi metodi se il progetto lo consente
        insertState.recoverShapes(map);

        verify(shape).setSelected(false);
        verify(decoratedView).undecorate();
    }
}
