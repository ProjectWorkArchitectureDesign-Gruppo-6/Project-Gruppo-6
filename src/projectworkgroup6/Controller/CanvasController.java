package projectworkgroup6.Controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.CanvasState;
import projectworkgroup6.State.SingleSelectState;
import projectworkgroup6.View.CanvasView;
import projectworkgroup6.View.ShapeView;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CanvasController implements StateObserver{


    @FXML
    private ScrollPane scrollPane;


    private MainController mainController;

    private Scene scene;

    private CanvasView canvasView;

    @FXML
    private Canvas canvas;


    @FXML
    private AnchorPane canvasPane;




    public void setCanvasView(CanvasView view) {
        this.canvasView = view;
    }

    /*aggiunto per la gestione del focus al canvas*/
    public Canvas getCanvas() {
        return canvas;
    }


    //// UTILIZZO COME OBSERVER IN BASE ALLO STATE ////  (Observer + State Pattern)

    //variabili di stato tenute in locale dopo la notifica
    private CanvasState currentState;
    private Color currentStroke;
    private Color currentFill;

    //Al ricevimento della notifica mantengo lo stato nuovo
    @Override
    public void onStateChanged(CanvasState newMode) {
        this.currentState = newMode;
        currentState.recoverShapes(map);
    }

    /////////////////////////////////////

    //// UTILIZZO OBSERVER IN BASE ALLE MODIFICHE SUL CANVAS //// (Observer + State Pattern)
    private Map<Shape,ShapeView> map;



    public void maybeExpandCanvasForShape(Collection<Shape> shapes) {
        final double MARGIN = 50;

        boolean expanded = false;
        double newWidth = canvasPane.getPrefWidth();
        double newHeight = canvasPane.getPrefHeight();

        for(Shape s:shapes){
            if (s.getX() + MARGIN > newWidth) {
                newWidth = s.getX() + MARGIN + s.getDim1();
                expanded = true;
                scrollPane.setHvalue(newWidth);
                break;
            }
            if (s.getY() + MARGIN > newHeight) {
                newHeight = s.getY() + MARGIN + s.getDim2();
                expanded = true;
                scrollPane.setVvalue(newHeight);
                break;
            }
        }
        if (expanded) {
            canvasPane.setPrefWidth(newWidth);
            canvasPane.setPrefHeight(newHeight);
            canvas.widthProperty().bind(canvasPane.widthProperty());
            canvas.heightProperty().bind(canvasPane.heightProperty());
        }
    }

    @Override
    public void onCanvasChanged(Map<Shape,ShapeView> map) {

        this.map = map;
        try{
            maybeExpandCanvasForShape(map.keySet());
            canvasView.render(map.values());
        }catch(NullPointerException e){
            System.out.println(" ");
        }
    }

    @Override
    public void onColorChanged(Color currentStroke, Color currentFill) {
        this.currentStroke = currentStroke;
        this.currentFill = currentFill;
        currentState.handleColorChanged(currentStroke, currentFill);
    }

    @Override
    public void onChangeFontColor(Color currentFontColor) {

    }

    @Override
    public void onChangeFontFamily(String currentFontName) {

    }


    /////////////////////////////////////


    //inizialize per ogni controller per la separazione dei ruoli, il canvasController deve riceve gli eventi del canvas
    //senza dipendere dal mainController
    @FXML
    public void initialize() {
        // Registrazione Observer pattern
        StateController.getInstance().addObserver(this);
        currentState = SingleSelectState.getInstance(); //Stato iniziale Select
        map = new HashMap<Shape,ShapeView>();
        currentStroke = new Color(0,0,0,1);


    }


    public void handleDelete(KeyEvent event) {

        System.out.println("Tasto premuto: " + event.getCode());
        currentState.handleDelete(event, map);
    }

    public void handleKeyTyped(KeyEvent event){
        System.out.println("Tasto premuto in editing: " + event.getCharacter());
        currentState.handleKeyTyped(event, map);
    }

    public void handleMouseReleased(double x, double y) {
        currentState.handleMouseReleased(x,y);
    }

    public void handleMouseDragged(double x, double y) {
        currentState.handleMouseDragged(x,y);
    }

    public void handlePression(double x, double y) {
        currentState.handlePression(x,y);
    }


    public void handleCanvasClick(double x, double y) { currentState.handleClick(x, y, map); }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }






    public void setScene() {
        scene = canvas.getScene();
        canvasView = new CanvasView(canvas, canvasPane,this, scene);
    }


    public void bindCanvasSize(ScrollPane pane) {
        map = new HashMap<Shape,ShapeView>();

        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());

        // Puoi anche aggiungere un listener per ridisegnare il contenuto dopo il resize
        canvas.widthProperty().addListener((obs, oldVal, newVal) -> onCanvasChanged(this.map));
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> onCanvasChanged(this.map));
    }


}
