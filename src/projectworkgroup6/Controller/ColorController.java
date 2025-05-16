/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectworkgroup6.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import projectworkgroup6.Model.ColorModel;

/**
 *
 * @author Dario
 */

public class ColorController {

    private ToolBarController toolBarController;
    private final ColorModel colorModel = new ColorModel(); // crea o inietta

    public void setToolBarController(ToolBarController toolBarController) {
        this.toolBarController = toolBarController;
    }

    public ColorModel getColorModel() {
        return colorModel;
    }

    @FXML
    private void ColorSelection(MouseEvent event) {
        Circle selectedCircle = (Circle) event.getSource();
        Color selectedColor = (Color) selectedCircle.getFill();

        // Converte il colore in stringa (es: 0xff0000ff)
        String colorString = toHexString(selectedColor);

        // Salva nel modello
        colorModel.setColor(colorString);

        System.out.println("Colore selezionato: " + colorString);
    }

    private String toHexString(Color color) {
        int a = (int) (color.getOpacity() * 255);
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        return String.format("0x%02x%02x%02x%02x", a, r, g, b);
    }
}
