package projectworkgroup6.Controller;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.Factory.TextBoxCreator;
import projectworkgroup6.Model.*;

import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Decorator.*;
import projectworkgroup6.View.ShapeView;
import projectworkgroup6.Command.*;
import java.util.List;
import java.util.ArrayList;
import projectworkgroup6.Model.Polygon;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import projectworkgroup6.State.SingleSelectState;
import projectworkgroup6.View.ShapeView;

import java.util.*;

public class DropDownController implements SelectionObserver {
    @FXML
    private AnchorPane dropDownMenuPane;

    private MainController mainController;
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
    public ComboBox modfontCombo;
    @FXML
    private Spinner modfontSizeSpinner;
    @FXML
    private ColorPicker modfontColorPicker;

    @FXML
    public void initialize() {

        // Popola fontCombo con i nomi dei font disponibili
        modfontCombo.setItems(FXCollections.observableArrayList(Font.getFamilies()));

        // Imposta un valore di default
        modfontCombo.getSelectionModel().select("Arial");

        // Configura fontSizeSpinner: valori da 8 a 72, step 1, valore iniziale 12
        modfontSizeSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 72, 12, 1)
        );

        modfontSizeSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                canvasController.onChangeFontSize((int)newValue); //non esiste onAction per lo spinner quindi setto l'azione nell'inizialize
            }
        });

        StateController.getInstance().addSelectionObserver(this);
    }

    @Override
    public void onShapeDeselected() {
       hideDDMenu();
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

    }

    public void showDDMenu(Shape s) {
        selectedShape = s;
        double x = s.getX();
        double width = s.getDim1();
        double y = s.getY();
        // Posiziona il menu con un piccolo offset
        dropDownMenuPane.setLayoutX(x + width/2 + 10);
        dropDownMenuPane.setLayoutY(y);
        dropDownMenuPane.setVisible(true);
        dropDownMenuPane.setManaged(true);


    }


    @FXML
    public void copy(ActionEvent event) {
        System.out.println("copia");
        ShapeView copiedShapeView = StateController.getInstance().getMap().get(selectedShape);
        setSavedView(copiedShapeView.undecorate());
        System.out.println(copiedShapeView.undecorate().getShape().getDim1());
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


    @FXML
    public void paste(ActionEvent event) {
        Shape originalShape = getSavedView().getShape();
        setSavedView(null);

        //richiama i metodi cloneAt di ogni modello e incolla alle coordinate del click specificate in questa modalità
        Shape newShape = originalShape.cloneAt(pasteX, pasteY);
        ShapeCreator creator = StateController.getInstance().getCreators().get(newShape.type());

        ShapeView newView = creator.createShapeView(newShape);
        newView = new BorderDecorator(newView, newShape.getBorder().toColor());
        newView = new FillDecorator(newView, newShape.getFill().toColor());

        InsertCommand cmd = new InsertCommand(newShape, newView);
        CommandManager.getInstance().executeCommand(cmd);

        dropDownMenu.getChildren().forEach(node -> node.setDisable(node == pasteBtn));
        hideDDMenu();
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
    void portaInPrimoPiano(ActionEvent event) {

            Shape s = selectedShape;
            if (s == null) return;
            Map<Shape, ShapeView> oldMap = StateController.getInstance().getMap();
            LinkedHashMap<Shape, ShapeView> newMap = new LinkedHashMap<>();

            for (Map.Entry<Shape, ShapeView> entry : oldMap.entrySet()) {
                if (!entry.getKey().equals(s)) {
                    newMap.put(entry.getKey(), entry.getValue());
                }
            }

            newMap.put(s, oldMap.get(s));
            StateController.getInstance().setMap(newMap);
            StateController.getInstance().redrawCanvas();
    }

    @FXML
    void portaInSecondoPiano(ActionEvent event) {
        Shape s = selectedShape;

        Map<Shape, ShapeView> oldMap = StateController.getInstance().getMap();
        LinkedHashMap<Shape, ShapeView> newMap = new LinkedHashMap<>();

        // Metti per prima la figura selezionata = "in secondo piano"
        newMap.put(s, oldMap.get(s));

        // Aggiungi tutte le altre mantenendo il loro ordine
        for (Map.Entry<Shape, ShapeView> entry : oldMap.entrySet()) {
            if (!entry.getKey().equals(s)) {
                newMap.put(entry.getKey(), entry.getValue());
            }
        }

        // Aggiorna lo stato
        StateController.getInstance().setMap(newMap);

        // Ridisegna il canvas
        StateController.getInstance().redrawCanvas();
    }

    //metodi per le modifiche del font dei textBox con i parametri selezionati dall'utente

    public void modifyFontColor(ActionEvent event) {
        Color fontColor = modfontColorPicker.getValue();
        canvasController.onChangeFontColor(fontColor);
    }

    public void modifyFontName(ActionEvent event) {
        String fontName = (String) modfontCombo.getValue();
        canvasController.onChangeFontFamily(fontName);
    }


    //per la funzionalità di salvataggio forme personalizzate
    @FXML
    private ToolBarController toolBarController;
    public void setToolBarController(ToolBarController toolBarController) {
        this.toolBarController = toolBarController;
    }

    public void onAddShape(ActionEvent event) {
        //passo la selectedShape come shape personalizzata al toolbarController
        ShapeView customShape = StateController.getInstance().getMap().get(selectedShape);
        toolBarController.addCustomShape(customShape);
    }
}
