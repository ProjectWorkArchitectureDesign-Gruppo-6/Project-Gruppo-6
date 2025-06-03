package projectworkgroup6.Controller;


import com.sun.javafx.css.converters.StringConverter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.DeleteCommand;
import projectworkgroup6.Command.InsertCommand;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.Decorator.FillDecorator;
import projectworkgroup6.Factory.*;

import projectworkgroup6.State.*;

import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.*;

import projectworkgroup6.View.ShapeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    private DropDownController dropDownController;

    public void setDropDownController(DropDownController dropDownController) {
        this.dropDownController = dropDownController;
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

    public void onMltSelectButton(ActionEvent actionEvent) {

        StateController.getInstance().setState(MultipleSelectState.getInstance());
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

    @FXML
    private GridPane buttons;

    private List<ShapeView> customShapeViews = new ArrayList<>();
    private int customShapeCounter = 1;

    /*serve a creare un link fra i controller del toolbar e del canvas */
    private CanvasController canvasController;

    public void setCanvasController(CanvasController canvasController) {
        this.canvasController = canvasController;
    }
    public void addCustomShape(ShapeView customShape) {
        customShapeViews.add(customShape); //inserisco la selected shape nelle shape personalizzate

        int index = customShapeViews.size() - 1; //faccio coincidere l'indice con la dimensione della lista
        TextInputDialog dialog = new TextInputDialog("Shape " + customShapeCounter);
        dialog.setTitle("Nuova Shape Personalizzata");
        dialog.setHeaderText("Inserisci un nome per la nuova shape:");
        dialog.setContentText("Nome:");

        Optional<String> result = dialog.showAndWait();
        if (!result.isPresent() || result.get().trim().isEmpty()) {
            // Se l'utente annulla o lascia vuoto, non aggiungere nulla
            return;
        }

        String nomeShape = result.get().trim();

        Button customBtn = new Button(nomeShape);
        customBtn.setPrefSize(60, 40);

        customBtn.setOnAction(e -> {
            canvasController.enableCustomShapeInsertion(customShapeViews.get(index));
        });

        int row = index / 2; //per posizionare i bottoni vicini a due a due
        int col = index % 2;

        buttons.add(customBtn, col, row); //aggiungo il bottone nel gridPane collegato allo scrollPane
    }

    @FXML
    private void undo(ActionEvent event) {
        CommandManager.getInstance().undoLastCommand();
        StateController.getInstance().redrawCanvas();
    }

    public void copym(ActionEvent event) {
        dropDownController.copy(event);
    }

    public void deletem(ActionEvent event) {

        StateController.getInstance().setState(new DeleteState());
    }

    public void cutm(ActionEvent event) {
        dropDownController.cut(event);
    }

}

