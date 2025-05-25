package projectworkgroup6.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import projectworkgroup6.Factory.EllipseCreator;
import projectworkgroup6.Factory.LineCreator;
import projectworkgroup6.Factory.RectangleCreator;
import projectworkgroup6.State.InsertState;
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
    @FXML
    private ColorPicker colorPickerBorder;
    @FXML
    private ColorPicker colorPickerFill;



    private MainController mainController;


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    


    @FXML
    public void initialize() {


    }


    // Handler per i pulsanti

    public void onSelectBtn(ActionEvent event) {
        StateController.getInstance().setState(SingleSelectState.getInstance());
        colorPickerBorder.setVisible(false);
        colorPickerFill.setVisible(false);
    }


    //il toolbar si occupa di catturare il click sul bottone
    //al click il cursore deve cambiare modalità, quindi deve essere modificato lo stato tramite state controller
    //notifico gli observer setCursorMode richiama notifyObservers permette di notificare gli Observer del cambio di modalità del cursore
    public void onRctBtn(ActionEvent event) {
        StateController.getInstance().setState(new InsertState(RectangleCreator.getInstance()));
        StateController.getInstance().notifyShapeDeselected();
        colorPickerBorder.setVisible(true);
        colorPickerFill.setVisible(true);

    }


    public void onElpBtn(ActionEvent actionEvent) {
        StateController.getInstance().setState(new InsertState(EllipseCreator.getInstance()));

        StateController.getInstance().notifyShapeDeselected();

        colorPickerBorder.setVisible(true);
        colorPickerFill.setVisible(true);


    }

    public void onLnBtn(ActionEvent actionEvent) {
        StateController.getInstance().setState(new InsertState(LineCreator.getInstance()));

        StateController.getInstance().notifyShapeDeselected();
        colorPickerBorder.setVisible(true);
        colorPickerFill.setVisible(false);

    }

    @FXML
    public void onStrokeColor(ActionEvent actionEvent) {
        Color borderColor = colorPickerBorder.getValue(); // Colore scelto dall'utente nella GUI
        StateController.getInstance().setStrokeColor(borderColor);

    }


    public void onFillColor(ActionEvent actionEvent) {
        Color fillColor = colorPickerFill.getValue();
        StateController.getInstance().setFillColor(fillColor);
    }
}