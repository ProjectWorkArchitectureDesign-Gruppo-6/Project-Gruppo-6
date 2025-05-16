package projectworkgroup6.Controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import projectworkgroup6.Model.CursorMode;

public class CanvasController implements CursorObserver{

    @FXML
    private Canvas canvas;

    private MainController mainController;

    private CursorMode cursorMode;

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
}
