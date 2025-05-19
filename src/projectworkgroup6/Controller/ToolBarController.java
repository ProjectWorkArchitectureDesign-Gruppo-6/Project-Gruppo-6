package projectworkgroup6.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import projectworkgroup6.Factory.EllipseCreator;
import projectworkgroup6.Factory.LineCreator;
import projectworkgroup6.Factory.RectangleCreator;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import projectworkgroup6.State.InsertState;
import projectworkgroup6.State.MultipleSelectState;
import projectworkgroup6.State.SingleSelectState;

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

    
    @FXML
    private AnchorPane colorPaletteContainer;

    @FXML
    public void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projectworkgroup6/View/Color.fxml"));
            AnchorPane colorPalette = loader.load();

            // Se vuoi accedere al controller della palette (per esempio per passare modelli o listener)
            ColorController colorController = loader.getController();
            // Puoi fare setup tipo: colorController.setToolBarController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Handler per i pulsanti

    public void onSelectBtn(ActionEvent event) {
        StateController.getInstance().setState(SingleSelectState.getInstance());
    }


    //il toolbar si occupa di catturare il click sul bottone
    //al click il cursore deve cambiare modalità, quindi deve essere modificato lo stato tramite state controller
    //notifico gli observer setCursorMode richiama notifyObservers permette di notificare gli Observer del cambio di modalità del cursore
    public void onRctBtn(ActionEvent event) {
        StateController.getInstance().setState(new InsertState(RectangleCreator.getInstance()));
    }


    public void onElpBtn(ActionEvent actionEvent) {
        StateController.getInstance().setState(new InsertState(EllipseCreator.getInstance()));
    }

    public void onLnBtn(ActionEvent actionEvent) {
        StateController.getInstance().setState(new InsertState(LineCreator.getInstance()));
    }

}