package projectworkgroup6.Controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.CanvasState;
import projectworkgroup6.State.SingleSelectState;
import projectworkgroup6.View.CanvasView;
import projectworkgroup6.View.ShapeView;

import java.util.HashMap;
import java.util.Map;

public class CanvasController implements StateObserver{



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




    //// UTILIZZO COME OBSERVER IN BASE ALLO STATE ////  (Observer + State Pattern)

    //variabili di stato tenute in locale dopo la notifica
    private CanvasState currentState;
    private Color currentStroke;

    //Al ricevimento della notifica mantengo lo stato nuovo
    @Override
    public void onStateChanged(CanvasState newMode) {
        this.currentState = newMode;
        currentState.recoverShapes(map);


    }

    /////////////////////////////////////

    //// UTILIZZO OBSERVER IN BASE ALLE MODIFICHE SUL CANVAS //// (Observer + State Pattern)
    private Map<Shape,ShapeView> map;



    @Override
    public void onCanvasChanged(Map<Shape,ShapeView> map) {
        this.map = map;
        canvasView.render(map.values());
    }

    @Override
    public void onColorChanged(Color currentStroke) {
        this.currentStroke = currentStroke;
        currentState.handleColorChanged(currentStroke);
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

    public void handleMouseReleased(double x, double y) {
        currentState.handleMouseReleased(x,y);
    }

    public void handleMouseDragged(double x, double y) {
        currentState.handleMouseDragged(x,y);
    }

    public void handleMoveClick(double x, double y) {
        currentState.handleMoveClick(x,y);
    }


    public void handleCanvasClick(double x, double y) { currentState.handleClick(x, y, map); }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }






    public void setScene() {
        scene = canvas.getScene();
        canvasView = new CanvasView(canvas, canvasPane,this, scene);
    }

    public void bindCanvasSize(AnchorPane pane) {
        map = new HashMap<Shape,ShapeView>();

        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());

        // Puoi anche aggiungere un listener per ridisegnare il contenuto dopo il resize
        canvas.widthProperty().addListener((obs, oldVal, newVal) -> onCanvasChanged(this.map));
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> onCanvasChanged(this.map));
    }

}
