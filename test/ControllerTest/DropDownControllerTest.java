package ControllerTest;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import projectworkgroup6.Controller.CanvasController;
import projectworkgroup6.Controller.DropDownController;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class DropDownControllerTest {

    private DropDownController controller;
    private CanvasController mockCanvasController;
    private Shape mockShape;

    @BeforeEach
    void setUp() {
        controller = new DropDownController();
        mockCanvasController = mock(CanvasController.class);
        controller.setCanvasController(mockCanvasController);

        mockShape = mock(Shape.class);
        when(mockShape.getBorder()).thenReturn(new ColorModel(0, 0, 0, 1.0));
        when(mockShape.getFill()).thenReturn(new ColorModel(255, 255, 255, 1.0));
        when(mockShape.getDim1()).thenReturn(100.0);
        when(mockShape.getDim2()).thenReturn(50.0);
        when(mockShape.getX()).thenReturn(100.0);
        when(mockShape.getY()).thenReturn(100.0);
        when(mockShape.getXc()).thenReturn(75.0);
        when(mockShape.getYc()).thenReturn(75.0);
    }

    @Test
    void testModifyStrokeWithColorDelegatesToCanvasController() {
        controller.setSelectedShapeForTest(mockShape);
        Color newFill = Color.RED;

        controller.testModifyStrokeWithColor(newFill);

        verify(mockCanvasController).onColorChanged(any(), eq(newFill));
    }

    @Test
    void testModifyFillWithColorDelegatesToCanvasController() {
        controller.setSelectedShapeForTest(mockShape);
        Color newStroke = Color.BLUE;

        controller.testModifyFillWithColor(newStroke);

        verify(mockCanvasController).onColorChanged(eq(newStroke), any());
    }

    @Test
    void testSetAndGetSavedView() {
        ShapeView mockView = mock(ShapeView.class);
        controller.setSavedView(mockView);
        assertEquals(mockView, controller.getSavedView());
    }
}