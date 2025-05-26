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
    private Shape mockShape;

    @BeforeEach
    public void setUp() {
        dropDownController = new DropDownController();
        mockCanvasController = mock(CanvasController.class);
        dropDownController.setCanvasController(mockCanvasController);

        // Crea uno shape di test (Rectangle)
        mockShape = new Rectangle(0,0,false,20,10,new ColorModel(0,0,0,1),new ColorModel(255,255,255,1));


        dropDownController.selectedShape = mockShape;
    }

    @Test
    public void testModifyStrokeDelegatesToCanvasController() {
        // Simulo che il ColorPicker restituisca RED (mock manuale)
        Color newStrokeColor = Color.RED;

        // Uso reflection per impostare il valore della borderPicker (solo se necessario)

        dropDownController.modifyStroke(new javafx.event.ActionEvent() {
        });

        // Forzato a mano perché non posso simulare ColorPicker senza GUI
        dropDownController.onShapeSelected(mockShape);
        dropDownController.modifyStroke(null); // Chiamata vera

        verify(mockCanvasController).onColorChanged(any(Color.class), eq(Color.BLUE));
    }

    @Test
    public void testModifyFillDelegatesToCanvasController() {
        dropDownController.onShapeSelected(mockShape);
        dropDownController.modifyFill(null); // Chiamata vera

        verify(mockCanvasController).onColorChanged(eq(Color.BLACK), any(Color.class));
    }
}
