package projectworkgroup6.Controller;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.Factory.TextBoxCreator;
import projectworkgroup6.Model.*;

import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Decorator.*;
import projectworkgroup6.View.GroupView;
import projectworkgroup6.View.ShapeView;
import projectworkgroup6.Command.*;
import java.util.List;
import java.util.ArrayList;
import projectworkgroup6.Model.Polygon;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import projectworkgroup6.State.SingleSelectState;
import projectworkgroup6.View.ShapeView;

import javax.xml.soap.Text;
import java.util.*;


/**
 * Controller del menu a tendina per la manipolazione delle shape selezionate.
 * Implementa l'interfaccia SelectionObserver per rispondere a selezioni/deselezioni.
 */

public class DropDownController implements SelectionObserver {
    @FXML
    private AnchorPane dropDownMenuPane;

    private MainController mainController;
    private CanvasController canvasController; // riferimento al canvasController che contiene il menù a tendina

    @FXML
    private HBox dropDownMenu;
    @FXML
    private Button copyBtn;
    @FXML
    private Button cutBtn;
    @FXML
    private Button pasteBtn;
    @FXML
    private Button ungroupBtn;

    @FXML
    private Button frontBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button portaInGiùBtn;
    @FXML
    private Button portaInSuBtn;;
    @FXML
    private ColorPicker borderPicker;
    @FXML
    private ColorPicker fillPicker;


    public Shape selectedShape = null;
    public ShapeView savedView = null;
    private double pasteX;
    private double pasteY;

    public ShapeView getSavedView() {
        return savedView;
    }

    public void setSavedView(ShapeView savedView) {
        this.savedView = savedView;
    }

    @FXML
    public ComboBox modfontCombo;
    @FXML
    private Spinner modfontSizeSpinner;
    @FXML
    private ColorPicker modfontColorPicker;

    @FXML
    Button addShape;

    @FXML
    TitledPane stylePane;
    @FXML
    Button GroupBtn;

    @FXML
    public void initialize() {

        // Popola fontCombo con i nomi dei font disponibili
        modfontCombo.setItems(FXCollections.observableArrayList(Font.getFamilies()));

        // Imposta un valore di default
        modfontCombo.getSelectionModel().select("Arial");

        // Configura fontSizeSpinner: valori da 8 a 72, step 1, valore iniziale 12
        modfontSizeSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 72, 12, 1)
        );

        modfontSizeSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                canvasController.onChangeFontSize((int)newValue); //non esiste onAction per lo spinner quindi setto l'azione nell'inizialize
            }
        });

        StateController.getInstance().addSelectionObserver(this);
    }

    @Override
    public void onShapeDeselected() {
        hideDDMenu();
    }

    @Override
    public void onMouseRightClick(double x, double y) {
        if (savedView != null) {
            showPasteMenu(x, y);
        }
    }
    public void showPasteMenu(double x, double y) {
        dropDownMenuPane.setLayoutX(x);
        dropDownMenuPane.setLayoutY(y);
        pasteX = x;
        pasteY = y;

        disableAllButtons();         // prima disabiliti tutto
        pasteBtn.setDisable(false);  // poi riattivi solo paste

        dropDownMenuPane.setVisible(true);
        dropDownMenuPane.setManaged(true);
    }

    //eccetto paste
    private void enableAllButtons() {

        copyBtn.setDisable(false);
        cutBtn.setDisable(false);
        addShape.setDisable(false);
        frontBtn.setDisable(false);
        backBtn.setDisable(false);
        portaInGiùBtn.setDisable(false);
        portaInSuBtn.setDisable(false);
        stylePane.setDisable(false);
        GroupBtn.setDisable(false);
        ungroupBtn.setDisable(false);
    }

    //eccetto paste
    private void disableAllButtons() {

        copyBtn.setDisable(true);
        cutBtn.setDisable(true);
        addShape.setDisable(true);
        frontBtn.setDisable(true);
        backBtn.setDisable(true);
        portaInGiùBtn.setDisable(true);
        portaInSuBtn.setDisable(true);
        stylePane.setDisable(true);
        GroupBtn.setDisable(true);
        ungroupBtn.setDisable(true);
    }

    @Override
    public void onShapeSelected(Shape s) {
        selectedShape = s;
        borderPicker.setValue(selectedShape.getBorder().toColor());
        fillPicker.setValue(selectedShape.getFill().toColor());
        showDDMenu(s);

    }



    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setCanvasController(CanvasController canvasController) { this.canvasController = canvasController;}



    public void hideDDMenu() {
        dropDownMenuPane.setVisible(false);
        dropDownMenuPane.setManaged(false);

    }

    public void showDDMenu(Shape s) {
        selectedShape = s;
        //centro della figura
        double x = s instanceof Polygon ? (s.getXc()+s.getDim1()/2) : s.getX();
        double width = s.getDim1();
        double y = s instanceof Polygon ? (s.getYc()+s.getDim2()/2) : s.getY();
        // Posiziona il menu con un piccolo offset
        dropDownMenuPane.setLayoutX(x + width/2 + 10);
        dropDownMenuPane.setLayoutY(y);
        dropDownMenuPane.setVisible(true);
        dropDownMenuPane.setManaged(true);

        ungroupBtn.setVisible(s instanceof Group);
        modfontColorPicker.setVisible(s instanceof TextBox);
        modfontCombo.setVisible(s instanceof TextBox);
        modfontSizeSpinner.setVisible(s instanceof TextBox);
        borderPicker.setVisible(!(s instanceof TextBox));
        fillPicker.setVisible(!(s instanceof TextBox));
        enableAllButtons();
    }


    @FXML
    public void copy(ActionEvent event) {
        System.out.println("copia");
        ShapeView copiedShapeView = StateController.getInstance().getMap().get(selectedShape);
        setSavedView(copiedShapeView.undecorate());

        hideDDMenu();
    }

    @FXML
    public void cut(ActionEvent event) {
        ShapeView copiedShapeView = StateController.getInstance().getMap().get(selectedShape);
        setSavedView(copiedShapeView.undecorate());
        hideDDMenu();
        ShapeView viewToDelete = StateController.getInstance().getMap().get(copiedShapeView.getShape());
        DeleteCommand cmd = new DeleteCommand(copiedShapeView.getShape(), viewToDelete);
        CommandManager.getInstance().executeCommand(cmd);
    }


    @FXML
    public void paste(ActionEvent event) {

        Map<Shape, ShapeView> map = StateController.getInstance().getMap();

        Shape originalShape = getSavedView().getShape();
        //setSavedView(null);

        //richiama i metodi cloneAt di ogni modello e incolla alle coordinate del click specificate in questa modalità
        Shape newShape = originalShape.cloneAt(pasteX, pasteY, StateController.getInstance().getMap().size() + 1);
        ShapeCreator creator = StateController.getInstance().getCreators().get(newShape.type());

        ShapeView newView;

        if(creator == null){


            processGroup((Group)newShape, StateController.getInstance().getCreators());
            newView = map.get(newShape);
            StateController.getInstance().removeShape(newShape,newView);


        }else {
            newView = creator.createShapeView(newShape);

        }

        System.out.println(newView);
        System.out.println(newShape);
        InsertCommand cmd = new InsertCommand(newShape, newView);
        CommandManager.getInstance().executeCommand(cmd);


        dropDownMenu.getChildren().forEach(node -> node.setDisable(node == pasteBtn));
        hideDDMenu();
    }

    private void processGroup(Group group, Map<String, ShapeCreator> creators) {

        Map<Shape,ShapeView> map = StateController.getInstance().getMap();

        // Prendo le shapes dal gruppo
        List<Shape> children = group.getShapes();

        // inizializzo le view oer ogni shape
        List<ShapeView> views = new ArrayList<>();

        // Per ogni shape del gruppo
        for (Shape child : children) {

            // Controllo se si tratta di un gruppo innestato
            if (child instanceof Group) {

                Group nestedGroup = (Group)child;

                // Ricorsione: processa il gruppo interno
                processGroup(nestedGroup, creators);

                // Aggiungo la vista del gruppo innestato nel gruppo esterno
                views.add(map.get(nestedGroup));

                // Rimuovi dalla mappa il gruppo annidato perchè viene compreso nel gruppo esterno
                StateController.getInstance().removeShape(nestedGroup,map.get(nestedGroup));


            } else {
                // È una shape normale
                ShapeCreator creator = creators.get(child.getClass().getSimpleName());
                ShapeView childView = creator.createShapeView(child);
                BorderDecorator border = new BorderDecorator(childView, child.getBorder().toColor());
                FillDecorator fill = new FillDecorator(border, child.getFill().toColor());

                views.add(fill);
            }
        }

        // Ora crea la view per il gruppo principale
        ShapeView groupView = new GroupView(group,views);

        StateController.getInstance().addShape(group, groupView);
    }



    @FXML
    public void modifyStroke(ActionEvent event) {

        Color border = borderPicker.getValue(); // Prendo il colore selezionato
        canvasController.onColorChanged(border,selectedShape.getFill().toColor()); // Delego al canvasController che agisce in base allo stato cui ci troviamo

    }

    @FXML
    public void modifyFill(ActionEvent event) {

        Color fill = fillPicker.getValue(); // Prendo colore selezionato
        System.out.println(selectedShape.getFill());
        canvasController.onColorChanged(selectedShape.getBorder().toColor(), fill); // Delego al canvas controller che agisce in base allo stato corrente

    }

    //Metodo che consente di mettere una figura selezionata davanti alle altre
    @FXML
    void portaInSu(ActionEvent event) {

        int currentLayer = selectedShape.getLayer();

        Map<Shape, ShapeView> map = StateController.getInstance().getMap();
        // Trova il layer massimo tra tutte le shape
        int maxLayer = map.keySet().stream()
                .mapToInt(Shape::getLayer)
                .max()
                .orElse(0);



        // Se è già in primo piano, non fare nulla
        if (currentLayer == maxLayer) return;

        int targetLayer = currentLayer + 1;

        // Cerca se esiste una shape con il layer target
        for (Shape s : map.keySet()) {
            if (s != selectedShape && s.getLayer() == targetLayer) {
                s.layerDown(); // scala indietro questa shape
                break; // ci sarà solo una shape con quel layer
            }
        }

        selectedShape.layerUp(); // porta avanti la shape selezionata

        StateController.getInstance().redrawCanvas();
    }


    @FXML
    void portaInGiù(ActionEvent event) {
        int currentLayer = selectedShape.getLayer();

        Map<Shape, ShapeView> map = StateController.getInstance().getMap();
        // Trova il layer massimo tra tutte le shape
        int minLayer = map.keySet().stream()
                .mapToInt(Shape::getLayer)
                .min()
                .orElse(0);



        // Se è già in primo piano, non fare nulla
        if (currentLayer == minLayer) return;

        int targetLayer = currentLayer - 1;

        // Cerca se esiste una shape con il layer target
        for (Shape s : map.keySet()) {
            if (s != selectedShape && s.getLayer() == targetLayer) {
                s.layerUp(); // scala indietro questa shape
                break; // ci sarà solo una shape con quel layer
            }
        }

        selectedShape.layerDown(); // porta avanti la shape selezionata

        StateController.getInstance().redrawCanvas();
    }


    @FXML
    void portaInPrimoPiano(ActionEvent event) {

        int currentLayer = selectedShape.getLayer();

        Map<Shape, ShapeView> map = StateController.getInstance().getMap();
        // Trova il layer massimo tra tutte le shape
        int maxLayer = map.keySet().stream()
                .mapToInt(Shape::getLayer)
                .max()
                .orElse(0);



        // Se è già in primo piano, non fare nulla
        if (currentLayer == maxLayer) return;


        // Cerca le shape con layer maggiore di quello selezionato
        for (Shape s : map.keySet()) {
            if (s != selectedShape && s.getLayer() > currentLayer) {
                s.layerDown(); // scala indietro questa shape
            }
        }

        selectedShape.setLayer(maxLayer); // porta in primo piano la shape selezionata

        StateController.getInstance().redrawCanvas();
    }

    //Metodo che consente di portare una figura selezionata in secondo piano
    @FXML
    void portaInSecondoPiano(ActionEvent event) {

        int currentLayer = selectedShape.getLayer();

        Map<Shape, ShapeView> map = StateController.getInstance().getMap();

        // Il layer minimo ha valore 1
        int minLayer = 1;

        // Se è già in primo piano, non fare nulla
        if (currentLayer == minLayer) return;


        for (Shape s : map.keySet()) {
            if (s != selectedShape && s.getLayer() < currentLayer) {
                s.layerUp(); // scala indietro questa shape             }
            }

            selectedShape.setLayer(minLayer); // porta la shape selezionata sullo sfondo

            StateController.getInstance().redrawCanvas();
        }


    }

    public void ungroup(ActionEvent actionEvent) {
        Map<Shape,ShapeView> map = StateController.getInstance().getMap();
        CommandManager.getInstance().executeCommand(new UngroupCommand((Group)selectedShape,(GroupView)map.get(selectedShape).undecorate()));
    }


    //metodi per le modifiche del font dei textBox con i parametri selezionati dall'utente

    public void modifyFontColor(ActionEvent event) {
        Color fontColor = modfontColorPicker.getValue();
        canvasController.onChangeFontColor(fontColor);
    }

    public void modifyFontName(ActionEvent event) {
        String fontName = (String) modfontCombo.getValue();
        canvasController.onChangeFontFamily(fontName);
    }


    //per la funzionalità di salvataggio forme personalizzate
    @FXML
    private ToolBarController toolBarController;
    public void setToolBarController(ToolBarController toolBarController) {
        this.toolBarController = toolBarController;
    }

    public void onAddShape(ActionEvent event) {
        //passo la selectedShape come shape personalizzata al toolbarController
        ShapeView customShape = StateController.getInstance().getMap().get(selectedShape);
        toolBarController.addCustomShape(customShape);
    }

    //Metodo usato per i test
    public void setSelectedShapeForTest(Shape shape) {
        this.selectedShape = shape;
    }

    //Metodo usato per i test senza JavaFX
    public void testModifyStrokeWithColor(Color newColor) {
        Color oldColor = selectedShape.getBorder().toColor();  // ex getBorderColor()
        canvasController.onColorChanged(oldColor, newColor);
    }

    //Metodo usato per i test senza JavaFX
    public void testModifyFillWithColor(Color newColor) {
        Color oldColor = selectedShape.getFill().toColor();  // ex getFillColor()
        canvasController.onColorChanged(oldColor, newColor);
    }

    public void makeGroup(ActionEvent event) {
        Group group = (Group) selectedShape;
        Map<Shape,ShapeView> map = StateController.getInstance().getMap();
        List<ShapeView> views = new ArrayList<>();
        for(Shape s : group.getShapes()){
            s.setSelected(false);
            views.add(map.get(s).undecorate()); // Tolgo la decorazione della selezione multipla
            StateController.getInstance().removeShape(s,map.get(s));
        }

        GroupView view = new GroupView(group,views);
        group.setLayer(StateController.getInstance().getMap().size() + 1);
        CommandManager.getInstance().executeCommand(new InsertGroupCommand(group,view));
        StateController.getInstance().setState(SingleSelectState.getInstance());
        System.out.println(group.getLayer());
    }
}