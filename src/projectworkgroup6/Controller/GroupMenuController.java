package projectworkgroup6.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.*;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.Decorator.FillDecorator;
import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Model.Group;
import projectworkgroup6.Model.Polygon;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.SingleSelectState;
import projectworkgroup6.View.GroupView;
import projectworkgroup6.View.ShapeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupMenuController implements GroupObserver{

    @FXML
    private AnchorPane groupMenuPane;

    private MainController mainController;
    private CanvasController canvasController; // riferimento al canvasController che contiene il menù a tendina

    @FXML
    private VBox GroupMenu;
    @FXML
    private Button copyBtn;
    @FXML
    private Button cutBtn;
    @FXML
    private Button GroupBtn;

    @FXML
    private Button pasteBtn;

    @FXML
    private ColorPicker borderPicker;
    @FXML
    private ColorPicker fillPicker;

    public Shape selectedShape = null;
    public List<ShapeView> savedView = null;
    private double pasteX;
    private double pasteY;

    public List<ShapeView> getSavedView() {
        return savedView;
    }

    public void setSavedView(List<ShapeView> savedView) {
        this.savedView = savedView;
    }

    @FXML
    public void initialize() {

        StateController.getInstance().addGroupObserver(this);
    }

    public void onShapeDeselected() {
        hideDDMenu();
    }

    @Override
    public void onMouseRightClick(double x, double y) {
        if (savedView != null) {
            showPasteMenu(x, y);
        }
    }
    public void showPasteMenu(double x,double y) {
        groupMenuPane.setLayoutX(x);
        groupMenuPane.setLayoutY(y);
        pasteX = x;
        pasteY = y;
        GroupMenu.getChildren().forEach(node -> node.setDisable(node != pasteBtn));

        groupMenuPane.setVisible(true);
        groupMenuPane.setManaged(true);
    }

    public void onShapeSelected(Shape s) {
        selectedShape = s;

        showDDMenu(s);

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setCanvasController(CanvasController canvasController) { this.canvasController = canvasController;}



    public void hideDDMenu() {
        groupMenuPane.setVisible(false);
        groupMenuPane.setManaged(false);

    }

    public void showDDMenu(Shape s) {

        selectedShape = s;
        double x = s.getX();
        double width = s.getDim1();
        double y = s.getY();

        // Posiziona il menu con un piccolo offset
        groupMenuPane.setLayoutX(x + width/2 + 10);
        groupMenuPane.setLayoutY(y);
        groupMenuPane.setVisible(true);
        groupMenuPane.setManaged(true);


    }


    @FXML
    public void copy(ActionEvent event) {

        // selectedShape è un gruppo
        Group group = (Group) selectedShape;
        // Carico le viste delle shape nel gruppo
        List<ShapeView> views = new ArrayList<>();
        Map<Shape,ShapeView> map = StateController.getInstance().getMap();

        for(Shape s : group.getShapes()){
            views.add(map.get(s).undecorate());
        }

        setSavedView(views);
        hideDDMenu();
    }

    @FXML
    public void cut(ActionEvent event) {}
    /*
    @FXML
    public void cut(ActionEvent event) {
        ShapeView copiedShapeView = StateController.getInstance().getMap().get(selectedShape);
        setSavedView(copiedShapeView.undecorate());
        hideDDMenu();
        ShapeView viewToDelete = StateController.getInstance().getMap().get(copiedShapeView.getShape());
        DeleteCommand cmd = new DeleteCommand(copiedShapeView.getShape(), viewToDelete);
        CommandManager.getInstance().executeCommand(cmd);
    }

     */

    @FXML
    public void paste(ActionEvent event) {}

/*
    @FXML
    public void paste(ActionEvent event) {
        System.out.println(originalShape.getDim1());
        setSavedView(null);
        ShapeCreator creator = StateController.getInstance().getCreators().get(originalShape.type());
        Map<Shape,ShapeView> map = StateController.getInstance().getMap(); // serve per layering

        Shape newShape;

        if (originalShape instanceof Polygon) {
            List<double[]> originalVertices = ((Polygon) originalShape).getVertices();

            // Calcolo il baricentro reale dei vertici
            double sumX = 0, sumY = 0;
            for (double[] v : originalVertices) {
                sumX += v[0];
                sumY += v[1];
            }
            double centerX = sumX / originalVertices.size();
            double centerY = sumY / originalVertices.size();

            // Calcolo lo spostamento rispetto alla posizione del click
            double dx = pasteX - centerX;
            double dy = pasteY - centerY;

            // Nuova lista di vertici spostati
            List<double[]> newVertices = new ArrayList<>();
            for (double[] v : originalVertices) {
                newVertices.add(new double[]{v[0] + dx, v[1] + dy});
            }

            newShape = new Polygon(new ArrayList<>(newVertices), false, originalShape.getBorder(), originalShape.getFill(), map.size() + 1, 0 );
        } else {
            newShape = creator.createShape(pasteX, pasteY, originalShape.getDim1(), originalShape.getDim2(), originalShape.getBorder(), originalShape.getFill(), map.size() + 1, 0);
        }

        ShapeView newView = creator.createShapeView(newShape);
        newView = new BorderDecorator(newView, newShape.getBorder().toColor());
        newView = new FillDecorator(newView, newShape.getFill().toColor());

        InsertCommand cmd = new InsertCommand(newShape, newView);
        CommandManager.getInstance().executeCommand(cmd);

        GroupMenu.getChildren().forEach(node -> node.setDisable(node == pasteBtn));
        hideDDMenu();
    }


 */

    public void makeGroup(ActionEvent actionEvent) {
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


    @FXML
    public void modifyStroke(ActionEvent event) {

        Color border = borderPicker.getValue(); // Prendo il colore selezionato
        canvasController.onColorChanged(border,selectedShape.getFill().toColor()); // Delego al canvasController che agisce in base allo stato cui ci troviamo

    }

    @FXML
    public void modifyFill(ActionEvent event) {

        Color fill = fillPicker.getValue(); // Prendo colore selezionato
        canvasController.onColorChanged(selectedShape.getBorder().toColor(), fill); // Delego al canvas controller che agisce in base allo stato corrente

    }
}
