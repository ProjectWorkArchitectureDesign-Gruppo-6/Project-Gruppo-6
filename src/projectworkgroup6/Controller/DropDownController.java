package projectworkgroup6.Controller;

import javafx.scene.paint.Color;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import projectworkgroup6.State.SingleSelectState;

import java.util.HashMap;

public class DropDownController implements SelectionObserver {
    public AnchorPane dropDownMenuPane;
    public MainController mainController;

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
       showDDMenu(s);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }



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
        System.out.println("modifica Stroke");
    }
    @FXML
    public void modifyFill(ActionEvent event) {
        System.out.println("modifica Fill");
    }
    @FXML
    public void rotate(ActionEvent event) {
        System.out.println("ruota");
    }
}
