package projectworkgroup6.Controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.CanvasState;
import projectworkgroup6.State.MultipleSelectState;
import projectworkgroup6.State.SingleSelectState;

import java.util.ArrayList;
import java.util.List;

public class CanvasController implements CursorObserver, CanvasObserver{

    @FXML
    private Canvas canvas;
    @FXML
    private AnchorPane canvasPane;

    private MainController mainController;

    private Scene scene;


    //// UTILIZZO COME OBSERVER IN BASE ALLO STATE ////  (Observer + State Pattern)

    //variabili di stato tenute in locale dopo la notifica
    private CanvasState currentState;

    //Al ricevimento della notifica mantengo lo stato nuovo
    @Override
    public void onStateChanged(CanvasState newMode) {
        this.currentState = newMode;
        currentState.recoverShapes(shapes);
    }

    /////////////////////////////////////

    //// UTILIZZO OBSERVER IN BASE ALLE MODIFICHE SUL CANVAS //// (Observer + State Pattern)
    private List<Shape> shapes;

    @Override
    public void onCanvasChanged(List<Shape> shapes) {
        this.shapes = shapes;
        redrawCanvas(shapes);
    }

    /////////////////////////////////////






    //inizialize per ogni controller per la separazione dei ruoli, il canvasController deve riceve gli eventi del canvas
    //senza dipendere dal mainController
    @FXML
    public void initialize() {
        // Registrazione Observer pattern
        StateController.getInstance().addObserver(this);
        StateController.getInstance().addCanvasObserver(this);
        currentState = SingleSelectState.getInstance(); //Stato iniziale Select
        shapes = new ArrayList<>();
        canvas.setOnMouseClicked(event -> handleCanvasClick(event.getX(), event.getY())); //Registrazione evento al click per inserimento
        canvas.setOnMousePressed(event -> handleMoveClick(event.getX(), event.getY())); // Registrazione evento al click su icona di movimento
        canvas.setOnMouseDragged(event -> handleMouseDragged(event.getX(), event.getY()));
        canvas.setOnMouseReleased(event -> handleMouseReleased(event.getX(), event.getY()));



    }

    @FXML
    private void handleDelete(KeyEvent event) {

        System.out.println("Tasto premuto: " + event.getCode());
        currentState.handleDelete(event, shapes);
    }

    private void handleMouseReleased(double x, double y) {
        currentState.handleMouseReleased(x,y);
    }

    private void handleMouseDragged(double x, double y) {
        currentState.handleMouseDragged(x,y);
    }

    private void handleMoveClick(double x, double y) {
        currentState.handleMoveClick(x,y);
    }


    private void handleCanvasClick(double x, double y) {
        currentState.handleClick(x, y, shapes);

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }




    private void redrawCanvas(List<Shape> shapes) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // cancella

        for (Shape s : shapes) {
            s.draw(gc);
        }
    }

    public void setScene() {
        scene = canvas.getScene();
        scene.setOnKeyPressed(event -> handleDelete(event));
    }
}
