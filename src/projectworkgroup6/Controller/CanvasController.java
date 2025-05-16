package projectworkgroup6.Controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import projectworkgroup6.Factory.RectangleCreator;
import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Model.CursorMode;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Model.CursorMode;

public class CanvasController implements CursorObserver{

    @FXML
    private Canvas canvas;

    private MainController mainController;

    private CursorMode cursorMode;

    private ShapeCreator currentCreator;

    public CursorMode getCursorMode() {
        return cursorMode;
    }

    public void setCursorMode(CursorMode cursorMode) {
        this.cursorMode = cursorMode;
    }

    public ShapeCreator getCurrentCreator() {
        return currentCreator;
    }

    public void setCurrentCreator(ShapeCreator currentCreator) {
        this.currentCreator = currentCreator;
    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void updateCursorMode(CursorMode mode){
        this.cursorMode = mode;
    }


    @Override
    public void onCursorModeChanged(CursorMode newMode) {
        this.cursorMode = newMode;
    }

    @Override
    public void onCurrentCreatorChanged(ShapeCreator currentCreator) {
        this.currentCreator = currentCreator;
    }


    //inizialize per ogni controller per la separazione dei ruoli, il canvasController deve riceve gli eventi del canvas
    //senza dipendere dal mainController
    @FXML
    public void initialize() {
        // Observer pattern
        StateController.getInstance().addObserver(this);
        canvas.setOnMouseClicked(event -> handleCanvasClick(event.getX(), event.getY()));
    }

    //uso factory method
    private void handleCanvasClick(double x, double y) {

        ShapeCreator creator = getCurrentCreator();
        if(creator != null){
            Shape shape = creator.createShape(x, y, javafx.scene.paint.Color.BLACK);
            shape.draw(canvas.getGraphicsContext2D());
        }

    }

}
