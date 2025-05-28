package projectworkgroup6.Controller;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.BorderDecorator;
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
    public void initialize() {

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
        System.out.println(originalShape.getDim1());
        setSavedView(null);
        ShapeCreator creator = StateController.getInstance().getCreators().get(originalShape.type());

        Shape newShape;

        if (originalShape instanceof Polygon) {
            List<double[]> originalVertices = ((Polygon) originalShape).getVertices();

            // Calcolo il baricentro reale dei vertici
            double sumX = 0, sumY = 0;
            for (double[] v : originalVertices) {
                sumX += v[0];
                sumY += v[1];
            }
            double centerX = sumX / originalVertices.size();
            double centerY = sumY / originalVertices.size();

            // Calcolo lo spostamento rispetto alla posizione del click
            double dx = pasteX - centerX;
            double dy = pasteY - centerY;

            // Nuova lista di vertici spostati
            List<double[]> newVertices = new ArrayList<>();
            for (double[] v : originalVertices) {
                newVertices.add(new double[]{v[0] + dx, v[1] + dy});
            }

            newShape = new Polygon(new ArrayList<>(newVertices), false, originalShape.getBorder(), originalShape.getFill());
        } else {
            newShape = creator.createShape(pasteX, pasteY, originalShape.getBorder(), originalShape.getFill());
        }

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

}
