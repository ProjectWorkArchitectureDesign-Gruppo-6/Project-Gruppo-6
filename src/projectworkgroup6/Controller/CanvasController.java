package projectworkgroup6.Controller;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.Command;
import projectworkgroup6.Command.ZoomCommand;
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
    private Group canvasGroup;
    @FXML
    private Canvas gridCanvas;

    @FXML
    private AnchorPane canvasPane;

    private GraphicsContext gridGC;
    private GraphicsContext drawGC;

    private int gridValue = 0;
    private double currentScale = 1.0;



    /*aggiunto per la gestione del focus al canvas*/
    public Canvas getCanvas() {
        return canvas;
    }

    private void zoomTo(Point2D click, double targetScale) {
        targetScale = targetScale * currentScale;

        // Calcolo centro del viewport
        double viewportWidth = scrollPane.getViewportBounds().getWidth();
        double viewportHeight = scrollPane.getViewportBounds().getHeight();

        currentScale = targetScale;

        // Applica la nuova scala
        canvasGroup.setScaleX(targetScale);
        canvasGroup.setScaleY(targetScale);


        // Forza il layout
        canvasGroup.applyCss();
        canvasGroup.layout();


        // Ottieni le dimensioni reali del contenuto scalato
        Bounds contentBounds = canvasGroup.getBoundsInParent();
        double contentWidth = contentBounds.getWidth();
        double contentHeight = contentBounds.getHeight();


        // Le coordinate del click nel group, a quali corrispondono nell'AnchorPane padre
        Point2D clickedInParent = canvasGroup.localToParent(click);
        System.out.println("click prima dello scale: "+click);
        System.out.println("cordinate del click nell'AnchorPane dopo lo scale: " + clickedInParent);


        // Distanza che il click dovrebbe avere dal bordo sinistro/superiore
        double targetX = clickedInParent.getX() - viewportWidth / 2.0;
        double targetY = clickedInParent.getY() - viewportHeight / 2.0;
        System.out.println(viewportWidth);
        System.out.println(viewportHeight);


        // Calcolo del valore di scroll Hvalue e Vvalue (proporzione tra 0 e 1)
        double vValue = targetX / (contentWidth - viewportWidth);
        double hValue = targetY / (contentHeight - viewportHeight);
        System.out.println(contentWidth-viewportWidth);
        System.out.println(contentHeight-viewportHeight);


        // Inverti segno perché il contenuto si è spostato, quindi lo scroll deve compensare
        scrollPane.setHvalue(vValue);
        scrollPane.setVvalue(hValue);

        // Ridisegna il contenuto se necessario
        onCanvasChanged(map);
        if (gridValue != 0) insertGrid(gridValue);
    }


    //Metodo per inserire una griglia, la cui dimensione delle celle è specificata dall'attributo value
    public void insertGrid(int value){
        if (value == 0) {
            gridCanvas.setVisible(false);
            gridValue = 0;
            return;
        }

        gridValue = value;
        gridCanvas.setVisible(true);

        double width = gridCanvas.getWidth();
        double height = gridCanvas.getHeight();

        // Controllo per evitare larghezze nulle o non inizializzate
        if (width <= 0 || height <= 0) {
            System.err.println("GridCanvas ha dimensioni invalide: " + width + " x " + height);
            return;
        }

        GraphicsContext gc = gridCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);

        double scale = canvasGroup.getScaleX();
        double translateX = canvasGroup.getTranslateX();
        double translateY = canvasGroup.getTranslateY();
        double spacing = value * scale;

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1.0);

        // Linee verticali
        for (double x = -translateX % spacing; x < width; x += spacing) {
            gc.strokeLine(x, 0, x, height);
        }

        // Linee orizzontali
        for (double y = -translateY % spacing; y < height; y += spacing) {
            gc.strokeLine(0, y, width, y);
        }
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
            gridCanvas.widthProperty().bind(canvasPane.widthProperty());
            gridCanvas.heightProperty().bind(canvasPane.heightProperty());



            if(gridValue != 0){
                insertGrid(gridValue);
            }
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

        gridGC = gridCanvas.getGraphicsContext2D();
        drawGC = canvas.getGraphicsContext2D();

        // Assoccia la dimensione del gridCanvas al container
        gridCanvas.widthProperty().bind(canvasPane.widthProperty());
        gridCanvas.heightProperty().bind(canvasPane.heightProperty());

        // Assoccia la dimensione del main canvas al container
        canvas.widthProperty().bind(canvasPane.widthProperty());
        canvas.heightProperty().bind(canvasPane.heightProperty());


        // Binding delle dimensioni del contenitore
        //canvasPane.minWidthProperty().bind(canvas.widthProperty().multiply(canvasGroup.scaleXProperty()));
        //canvasPane.minHeightProperty().bind(canvas.heightProperty().multiply(canvasGroup.scaleYProperty()));
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
        currentState.handlePressionRotate(x, y);
        currentState.handlePression(x,y);
    }

    public void handleCanvasClick(MouseEvent e, double x, double y) { currentState.handleClick(e,x, y, map); }

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
        gridCanvas.widthProperty().bind(pane.widthProperty());
        gridCanvas.heightProperty().bind(pane.heightProperty());


        // Puoi anche aggiungere un listener per ridisegnare il contenuto dopo il resize
        canvas.widthProperty().addListener((obs, oldVal, newVal) -> onCanvasChanged(this.map));
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> onCanvasChanged(this.map));
        gridCanvas.widthProperty().addListener((obs, oldVal, newVal) -> onCanvasChanged(this.map));
        gridCanvas.heightProperty().addListener((obs, oldVal, newVal) -> onCanvasChanged(this.map));

    }


    public void setZoomClick(Point2D clickInCanvas, double zoom) {
        Point2D clickInScene = canvas.localToScene(clickInCanvas);
        Point2D clickInGroup = canvasGroup.sceneToLocal(clickInScene);
        zoomTo(clickInGroup, zoom);
    }
}
