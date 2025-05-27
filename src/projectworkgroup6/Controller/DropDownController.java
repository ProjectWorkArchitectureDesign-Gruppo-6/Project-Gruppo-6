package projectworkgroup6.Controller;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Controller.CanvasController;
import projectworkgroup6.Controller.MainController;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.*;
import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Decorator.*;
import projectworkgroup6.View.ShapeView;
import projectworkgroup6.Command.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import projectworkgroup6.State.SingleSelectState;

import java.util.HashMap;

public class DropDownController implements SelectionObserver {
    public AnchorPane dropDownMenuPane;
    public MainController mainController;
    private CanvasController canvasController; // riferimento al canvasController che contiene il menù a tendina

    @FXML
    private VBox dropDownMenu;
    @FXML
    private Button copyBtn;
    @FXML
    private Button cutBtn;
    @FXML
    private Button pasteBtn;
    @FXML
    private Button rotateBtn;
    @FXML
    private Button frontBtn;
    @FXML
    private Button backBtn;
    @FXML
    private ColorPicker borderPicker;
    @FXML
    private ColorPicker fillPicker;

    public Shape selectedShape = null;
    public ShapeView savedView = null;
    private double pasteX;
    private double pasteY;

    public ShapeView getSavedView() {
        return savedView;
    }

    public void setSavedView(ShapeView savedView) {
        this.savedView = savedView;
    }

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

    @Override
    public void onMouseRightClick(double x, double y) {
        if (savedView != null) {
            showPasteMenu(x, y);
        }
    }

    public void showPasteMenu(double x,double y) {
        dropDownMenuPane.setLayoutX(x);
        dropDownMenuPane.setLayoutY(y);
        pasteX = x;
        pasteY = y;
        dropDownMenu.getChildren().forEach(node -> node.setDisable(node != pasteBtn));

        dropDownMenuPane.setVisible(true);
        dropDownMenuPane.setManaged(true);
    }

    public void setMainController(MainController mainController) {
    this.mainController = mainController;
}
public void setCanvasController(CanvasController canvasController) {
    this.canvasController = canvasController;
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
        ShapeView copiedShapeView = StateController.getInstance().getMap().get(selectedShape);
        setSavedView(copiedShapeView.undecorate());
        hideDDMenu();
    }

    @FXML
    public void cut(ActionEvent event) {
        ShapeView copiedShapeView = StateController.getInstance().getMap().get(selectedShape);
        setSavedView(copiedShapeView.undecorate());
        hideDDMenu();
        ShapeView viewToDelete = StateController.getInstance().getMap().get(copiedShapeView.getShape());
        DeleteCommand cmd = new DeleteCommand(copiedShapeView.getShape(), viewToDelete);
        CommandManager.getInstance().executeCommand(cmd);
    }

@FXML //stampa UNA volta una figura
public void paste(ActionEvent event) {
    Shape myShape = getSavedView().getShape();
    setSavedView(null);
    ShapeCreator creator = StateController.getInstance().getCreators().get(myShape.type());

    Shape newShape = creator.createShape(pasteX, pasteY, myShape.getBorder(), myShape.getFill() );
    ShapeView newView = creator.createShapeView(newShape);

    newView = new BorderDecorator(newView,newShape.getBorder().toColor());
    newView = new FillDecorator(newView,newShape.getFill().toColor());

    InsertCommand cmd = new InsertCommand(newShape,newView);
    CommandManager.getInstance().executeCommand(cmd);

    dropDownMenu.getChildren().forEach(node -> node.setDisable(node==pasteBtn));
    hideDDMenu();
    //
    setSavedView(null);
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
    @FXML
    public void toFront(ActionEvent event) {

    }

    @FXML
    public void toBack(ActionEvent event) {

    }

}
