package projectworkgroup6.Controller;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.Model.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import projectworkgroup6.State.SingleSelectState;

import java.util.HashMap;

public class DropDownController implements SelectionObserver {
    @FXML
    private AnchorPane dropDownMenuPane;

    private MainController mainController;
    private CanvasController canvasController; // riferimento al canvasController che contiene il menù a tendina


    @FXML
    private ColorPicker borderPicker;

    @FXML
    private ColorPicker fillPicker;

    public Shape selectedShape = null;

    @FXML
    public void initialize() {

        StateController.getInstance().addSelectionObserver(this);
    }

    @Override
    public void onShapeDeselected() {
       hideDDMenu();
    }

    @Override
    public void onShapeSelected(Shape s) {
        selectedShape = s;
        borderPicker.setValue(selectedShape.getBorder().toColor());
        fillPicker.setValue(selectedShape.getFill().toColor());
        System.out.println(selectedShape);
        showDDMenu(s);

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setCanvasController(CanvasController canvasController) { this.canvasController = canvasController;}



    public void hideDDMenu() {
        dropDownMenuPane.setVisible(false);
        dropDownMenuPane.setManaged(false);
        System.out.println("Menu a tendina NASCOSTO");
    }

    public void showDDMenu(Shape s) {
        selectedShape = s;
        double x = s.getX();
        double y = s.getY();
        // Posiziona il menu con un piccolo offset
        dropDownMenuPane.setLayoutX(x+60.0);
        dropDownMenuPane.setLayoutY(y);
        dropDownMenuPane.setVisible(true);
        dropDownMenuPane.setManaged(true);

        System.out.println("Menu a tendina visibile");
    }



    @FXML
    public void copy(ActionEvent event) {
        System.out.println("copia");
/* METODO DI PROVA
        Shape s = selectedShape;
        if (s instanceof Rectangle) {
            System.out.println("RETTANGOLO");
        }
        if (s instanceof Ellipse) {
            System.out.println("ELLI");
        }
        if (s instanceof Line) {
            System.out.println("LINEA");
        }*/
    }
    @FXML
    public void paste(ActionEvent event) {
        System.out.println("incolla");
    }
    @FXML
    public void cut(ActionEvent event) {
        System.out.println("Taglia");
    }
    @FXML
    public void modifyStroke(ActionEvent event) {

        Color border = borderPicker.getValue(); // Prendo il colore selezionato
        canvasController.onColorChanged(border,selectedShape.getFill().toColor()); // Delego al canvasController che agisce in base allo stato cui ci troviamo

    }
    @FXML
    public void modifyFill(ActionEvent event) {

        Color fill = fillPicker.getValue(); // Prendo colore selezionato
        System.out.println(selectedShape.getFill());
        canvasController.onColorChanged(selectedShape.getBorder().toColor(), fill); // Delego al canvas controller che agisce in base allo stato corrente

    }
    @FXML
    public void rotate(ActionEvent event) {
        System.out.println("ruota");
    }


}
