package Test;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Controller.StateObserver;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.CanvasState;
import projectworkgroup6.View.ShapeView;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StateControllerTest {

    private StateController stateController;

    @BeforeEach
    void setUp() {
        stateController = StateController.getInstance();
    }

    @Test
    void testSingletonReturnsSameInstance() {
        StateController secondInstance = StateController.getInstance();
        assertSame(stateController, secondInstance);
    }

    @Test
    void testAddObserverOnlyOnce() {
        StateObserver observer = mock(StateObserver.class);
        stateController.addObserver(observer);
        stateController.addObserver(observer); // doppio tentativo

        CanvasState newState = mock(CanvasState.class);
        stateController.setState(newState);

        verify(observer, times(1)).onStateChanged(newState);
    }

    @Test
    void testSetStateNotifiesObservers() {
        StateObserver observer = mock(StateObserver.class);
        stateController.addObserver(observer);

        CanvasState newState = mock(CanvasState.class);
        stateController.setState(newState);

        verify(observer).onStateChanged(newState);
    }

    @Test
    void testAddShapeUpdatesMapAndNotifies() {
        StateObserver observer = mock(StateObserver.class);
        stateController.addObserver(observer);

        Shape shape = mock(Shape.class);
        ShapeView shapeView = mock(ShapeView.class);

        stateController.addShape(shape, shapeView);

        Map<Shape, ShapeView> map = stateController.getMap();
        assertTrue(map.containsKey(shape));
        verify(observer).onCanvasChanged(map);
    }

    @Test
    void testRemoveShapeUpdatesMapAndNotifies() {
        StateObserver observer = mock(StateObserver.class);
        stateController.addObserver(observer);

        Shape shape = mock(Shape.class);
        ShapeView shapeView = mock(ShapeView.class);

        stateController.addShape(shape, shapeView);
        stateController.removeShape(shape, shapeView);

        Map<Shape, ShapeView> map = stateController.getMap();
        assertFalse(map.containsKey(shape));
        verify(observer, times(2)).onCanvasChanged(map); // una per add, una per remove
    }

    @Test
    void testSetStrokeColorNotifiesObservers() {
        StateObserver observer = mock(StateObserver.class);
        stateController.addObserver(observer);

        Color newColor = Color.RED;
        stateController.setStrokeColor(newColor);

        verify(observer).onColorChanged(newColor);
    }
}
