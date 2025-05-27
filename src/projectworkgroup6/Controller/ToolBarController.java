package projectworkgroup6.Controller;


import com.sun.javafx.css.converters.StringConverter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import projectworkgroup6.Factory.*;
import projectworkgroup6.State.InsertPolygonState;
import projectworkgroup6.State.InsertState;

import projectworkgroup6.State.InsertTextBoxState;
import projectworkgroup6.State.SingleSelectState;

public class ToolBarController {


    @FXML
    public ComboBox fontCombo;
    @FXML
    private Spinner fontSizeSpinner;
    @FXML
    private ColorPicker fontColorPicker;
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
        // Popola fontCombo con i nomi dei font disponibili
        fontCombo.setItems(FXCollections.observableArrayList(Font.getFamilies()));

        // Imposta un valore di default
        fontCombo.getSelectionModel().select("Arial");

        // Configura fontSizeSpinner: valori da 8 a 72, step 1, valore iniziale 12
        fontSizeSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 72, 12, 1)
        );

        // Aggiunta di un listener per le azioni effettuate sullo Spinner perchè non è possibile settare onAction
        fontSizeSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                StateController.getInstance().setFontSize((int)newValue);//richiamo lo StateController passandogli il valore selezionato
            }
        });

    }


    // Handler per i pulsanti

    public void onSelectBtn(ActionEvent event) {
        StateController.getInstance().setState(SingleSelectState.getInstance());

        colorPickerBorder.setVisible(false);
        colorPickerFill.setVisible(false);
        fontCombo.setVisible(false);
        fontColorPicker.setVisible(false);
        fontSizeSpinner.setVisible(false);

    }


    //il toolbar si occupa di catturare il click sul bottone
    //al click il cursore deve cambiare modalità, quindi deve essere modificato lo stato tramite state controller
    //notifico gli observer setCursorMode richiama notifyObservers permette di notificare gli Observer del cambio di modalità del cursore
    public void onRctBtn(ActionEvent event) {
        StateController.getInstance().setState(new InsertState(RectangleCreator.getInstance()));
        StateController.getInstance().notifyShapeDeselected();
        colorPickerBorder.setVisible(true);
        colorPickerFill.setVisible(true);
        fontCombo.setVisible(false);
        fontColorPicker.setVisible(false);
        fontSizeSpinner.setVisible(false);
    }


    public void onElpBtn(ActionEvent actionEvent) {
        StateController.getInstance().setState(new InsertState(EllipseCreator.getInstance()));
        StateController.getInstance().notifyShapeDeselected();
        colorPickerBorder.setVisible(true);
        colorPickerFill.setVisible(true);
        fontCombo.setVisible(false);
        fontColorPicker.setVisible(false);
        fontSizeSpinner.setVisible(false);
    }

    public void onLnBtn(ActionEvent actionEvent) {
        StateController.getInstance().setState(new InsertState(LineCreator.getInstance()));
        StateController.getInstance().notifyShapeDeselected();
        colorPickerBorder.setVisible(true);
        colorPickerFill.setVisible(false);
        fontCombo.setVisible(false);
        fontColorPicker.setVisible(false);
        fontSizeSpinner.setVisible(false);
    }

    @FXML
    public void onStrokeColor(ActionEvent actionEvent) {
        Color borderColor = colorPickerBorder.getValue(); // Colore scelto dall'utente nella GUI
        StateController.getInstance().setStrokeColor(borderColor);
        fontCombo.setVisible(false);
        fontColorPicker.setVisible(false);
        fontSizeSpinner.setVisible(false);
    }


    public void onFillColor(ActionEvent actionEvent) {
        Color fillColor = colorPickerFill.getValue();
        StateController.getInstance().setFillColor(fillColor);
        fontCombo.setVisible(false);
        fontColorPicker.setVisible(false);
        fontSizeSpinner.setVisible(false);
    }


    public void onPlsBtn(ActionEvent actionEvent) {
        StateController.getInstance().setState(new InsertPolygonState(PolygonCreator.getInstance()));
        StateController.getInstance().notifyShapeDeselected();
        colorPickerBorder.setVisible(true);
        colorPickerFill.setVisible(true);
        fontCombo.setVisible(false);
        fontColorPicker.setVisible(false);
        fontSizeSpinner.setVisible(false);
    }

    public void onTxtBtn(ActionEvent actionEvent) {
        StateController.getInstance().setState(new InsertTextBoxState(TextBoxCreator.getInstance())); //se faccio click sul bottone di casella di testo entro nello stato InsertTextBoxState
        fontCombo.setVisible(true); //rendo visibili solo al click del bottone le opzioni per il testp
        fontColorPicker.setVisible(true);
        fontSizeSpinner.setVisible(true);
        colorPickerBorder.setVisible(false); //rendo invisibile il colorPicker delle shape
        colorPickerFill.setVisible(false);
        StateController.getInstance().notifyShapeDeselected();

    }

    public void onFontColor(ActionEvent actionEvent) { //agendo sul fontColor prendi il colore selezionato dall'utente
        Color fontColor = fontColorPicker.getValue();
        StateController.getInstance().setFontColor(fontColor);
    }

    public void onFontName(ActionEvent actionEvent) { //agendo sul comboBox prendi la stringa fontName selezionata dall'utente
        String fontName = (String) fontCombo.getValue();
        StateController.getInstance().setFontFamily(fontName); //viene passata allo StateController tramite setFontFamily
    }
}

