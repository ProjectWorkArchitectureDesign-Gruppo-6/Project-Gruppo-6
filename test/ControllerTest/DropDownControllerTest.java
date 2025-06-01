package ControllerTest;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Controller.CanvasController;
import projectworkgroup6.Controller.DropDownController;

import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.Model.Shape;

import static org.mockito.Mockito.*;

public class DropDownControllerTest {

    private DropDownController dropDownController;
    private CanvasController mockCanvasController;
    private Shape testShape;

    @BeforeEach
    public void setUp() {
        dropDownController = new DropDownController();
        mockCanvasController = mock(CanvasController.class);
        dropDownController.setCanvasController(mockCanvasController);

        testShape = new Rectangle(0, 0, false, 20, 10,
                new ColorModel(0, 0, 0, 1),      // border: black
                new ColorModel(255, 255, 255, 1) // fill: white
        );

        dropDownController.setSelectedShapeForTest(testShape);
    }

    @Test
    public void testModifyStrokeDelegatesToCanvasController() {
        dropDownController.testModifyStrokeWithColor(Color.RED);
        verify(mockCanvasController).onColorChanged(eq(Color.BLACK), eq(Color.RED));
    }

    @Test
    public void testModifyFillDelegatesToCanvasController() {
        dropDownController.testModifyFillWithColor(Color.BLUE);
        verify(mockCanvasController).onColorChanged(eq(Color.WHITE), eq(Color.BLUE));
    }
}