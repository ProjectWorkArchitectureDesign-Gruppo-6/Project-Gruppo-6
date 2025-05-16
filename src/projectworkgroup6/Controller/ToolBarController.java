package projectworkgroup6.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import projectworkgroup6.Factory.EllipseCreator;
import projectworkgroup6.Factory.LineCreator;
import projectworkgroup6.Factory.RectangleCreator;
import projectworkgroup6.Model.CursorMode;

public class ToolBarController {

    @FXML
    private Button rctBtn;
    @FXML
    private Button ellipsoidBtn;
    @FXML
    private Button lineBtn;
    @FXML
    private Button addShapeBtn;
    @FXML
    private Button slcBtn;



    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // Handler per i pulsanti

    public void onSelectBtn(ActionEvent event) {
        StateController.getInstance().setCursorMode(CursorMode.SELECT, null);
    }


    //il toolbar si occupa di catturare il click sul bottone
    //al click il cursore deve cambiare modalità, quindi deve essere modificato lo stato tramite state controller
    //notifico gli observer setCursorMode richiama notifyObservers permette di notificare gli Observer del cambio di modalità del cursore
    public void onRctBtn(ActionEvent event) {
        StateController.getInstance().setCursorMode(CursorMode.INSERT_RECTANGLE, RectangleCreator.getInstance());
    }


    public void onElpBtn(ActionEvent actionEvent) {
        StateController.getInstance().setCursorMode(CursorMode.INSERT_ELLIPSE, EllipseCreator.getInstance());
    }

    public void onLnBtn(ActionEvent actionEvent) {
        StateController.getInstance().setCursorMode(CursorMode.INSERT_LINE, LineCreator.getInstance());
    }
}