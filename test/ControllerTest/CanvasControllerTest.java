package ControllerTest;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Controller.CanvasController;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.CanvasState;
import projectworkgroup6.View.ShapeView;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class CanvasControllerTest {

    private CanvasController canvasController;
    private CanvasState mockState;

    @BeforeEach
    public void setUp() {
        canvasController = new CanvasController();
        mockState = mock(CanvasState.class);
    }

    @Test
    public void testOnStateChangedCallsRecoverShapes() {
        Map<Shape, ShapeView> map = new HashMap<>();
        Shape mockShape = mock(Shape.class);
        ShapeView mockView = mock(ShapeView.class);
        map.put(mockShape, mockView);

        // Inietto lo stato interno (map) con riflessione se serve, oppure simulazione indiretta
        canvasController.onCanvasChanged(map);

        canvasController.onStateChanged(mockState);

        verify(mockState).recoverShapes(map);
    }

    @Test
    public void testOnColorChangedCallsHandleColorChanged() {
        canvasController.onStateChanged(mockState); // imposto lo stato corrente

        Color stroke = Color.RED;
        Color fill = Color.BLUE;

        canvasController.onColorChanged(stroke, fill);

        verify(mockState).handleColorChanged(stroke, fill);
    }
}
