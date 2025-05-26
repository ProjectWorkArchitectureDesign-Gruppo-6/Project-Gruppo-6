package projectworkgroup6.Controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
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

import java.util.HashMap;
import java.util.Map;

public class CanvasController implements StateObserver{



    private MainController mainController;

    private Scene scene;

    private CanvasView canvasView;

    @FXML
    private Canvas canvas;
    @FXML
    private Group canvasGroup;
    @FXML
    private AnchorPane canvasPane;
    @FXML
    private AnchorPane canvasContainer;
    @FXML
    private Canvas gridCanvas;
    @FXML
    private AnchorPane canvasLayer;

    private GraphicsContext gridGC;
    private GraphicsContext drawGC;

    private int gridValue = 0;




    public void setCanvasView(CanvasView view) {
        this.canvasView = view;
    }


    //Metodo per effettuare zoom in o zoom out in base al valore specificato dall'attributo targetScale
    public void zoomTo(double targetScale) {
        double currentScale = canvasGroup.getScaleX();
        double factor = targetScale / currentScale;

        Command zoom = new ZoomCommand(canvasGroup, factor);
        zoom.execute();

        if (gridValue != 0) {
            
            insertGrid(gridValue);  // ridisegna griglia in base al nuovo scale
            System.out.println("Griglia aggiornata con valore " + gridValue);
        } else {
            System.out.println("Griglia non attiva (valore 0)");
        }

        // Se hai uno stack per undo:
        // CommandManager.getInstance().addCommand(zoom);
    }

    //Metodo per inserire una griglia, la cui dimensione delle celle è specificata dall'attributo value
    public void insertGrid(int value){
        GraphicsContext gc = gridCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gridCanvas.getWidth(), gridCanvas.getHeight());

        double scale = canvasGroup.getScaleX();  // zoom attuale
        double translateX = canvasGroup.getTranslateX();  // traslazione X
        double translateY = canvasGroup.getTranslateY();  // traslazione Y

        double spacing = value * scale;  // distanza tra le linee in base allo zoom

        double width = gridCanvas.getWidth();
        double height = gridCanvas.getHeight();

        gc.setStroke(Color.LIGHTGRAY);
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



    @Override
    public void onCanvasChanged(Map<Shape,ShapeView> map) {

        this.map = map;
        try{
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

        AnchorPane.setTopAnchor(gridCanvas, 0.0);
        AnchorPane.setBottomAnchor(gridCanvas, 0.0);
        AnchorPane.setLeftAnchor(gridCanvas, 0.0);
        AnchorPane.setRightAnchor(gridCanvas, 0.0);

        gridGC = gridCanvas.getGraphicsContext2D();
        drawGC = canvas.getGraphicsContext2D();

        // Bind gridCanvas size to container
        gridCanvas.widthProperty().bind(canvasLayer.widthProperty());
        gridCanvas.heightProperty().bind(canvasLayer.heightProperty());

        // Bind main canvas size to container
        canvas.widthProperty().bind(canvasLayer.widthProperty());
        canvas.heightProperty().bind(canvasLayer.heightProperty());


        // Binding delle dimensioni del contenitore
        //canvasContainer.minWidthProperty().bind(canvas.widthProperty().multiply(canvasGroup.scaleXProperty()));
        //canvasContainer.minHeightProperty().bind(canvas.heightProperty().multiply(canvasGroup.scaleYProperty()));

        //javafx.application.Platform.runLater(this::setScene);

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

    public void bindCanvasSize(AnchorPane pane) {
        map = new HashMap<Shape,ShapeView>();

        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());

        // Puoi anche aggiungere un listener per ridisegnare il contenuto dopo il resize
        canvas.widthProperty().addListener((obs, oldVal, newVal) -> onCanvasChanged(this.map));
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> onCanvasChanged(this.map));

    }

}
