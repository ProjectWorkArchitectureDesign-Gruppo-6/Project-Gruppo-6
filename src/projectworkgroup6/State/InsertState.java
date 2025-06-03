package projectworkgroup6.State;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.DeleteCommand;
import projectworkgroup6.Command.InsertCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.Decorator.FillDecorator;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import java.util.HashMap;
import java.util.Map;

// Nello stato di Inserimento, ci occupiamo dell'inserimento delle figure.

public class InsertState implements CanvasState {


    private final ShapeCreator creator; //utilizzo del Factory
    private Color currentStroke = StateController.getInstance().getStrokeColor(); // Setto colore di default in caso non venga selezionato
    private Color currentFill = StateController.getInstance().getFillColor();

    private double defaultWidth = 100;
    private double defaultHeight = 50;

    private ColorModel border, fill;

    public InsertState(ShapeCreator creator) {
        this.creator = creator;
    }

    @Override
    public void handleClick(MouseEvent event,double x, double y, Map<Shape, ShapeView> map) {  //Creazione della Shape e della ShapeView

        // Converto il colore selezionato per salvarlo nel model
        border = ColorModel.fromColor(currentStroke);
        fill = ColorModel.fromColor(currentFill);

        // Creo Shape e View corrispondente in base al colore scelto
        Shape shape = creator.createShape(x, y, defaultWidth, defaultHeight, border, fill, map.size() + 1, 0);
        ShapeView shapeView = creator.createShapeView(shape);
        shapeView = new BorderDecorator(shapeView, currentStroke);
        shapeView = new FillDecorator(shapeView, currentFill);

        // Inserisco Shape e View nello stato (undoable)
        InsertCommand cmd = new InsertCommand(shape, shapeView);
        CommandManager.getInstance().executeCommand(cmd);
        System.out.println(shape.getXc() + shape.getDim1() / 2.0);
        System.out.println(shape.getYc() + shape.getDim2() / 2.0);


    }

    @Override
    public void handlePression(double x, double y) {
        //System.out.println("Non definito");
    }

    @Override
    public void handlePressionRotate(double x, double y) {

    }

    @Override
    public void handleMouseDragged(double x, double y) {
        //System.out.println("Non definito");
    }

    @Override
    public void handleMouseReleased(double x, double y) {
        //System.out.println("Non definito");
    }

    @Override
    public void recoverShapes(Map<Shape, ShapeView> map) {
        Map<Shape, ShapeView> copy = new HashMap<Shape, ShapeView>(map);
        for (Map.Entry<Shape, ShapeView> entry : copy.entrySet()) {
            Shape s = entry.getKey();
            ShapeView v = entry.getValue();

            // Deseleziona logicamente
            s.setSelected(false);

            // Rimuovi dal gruppo provvisorio
            s.setGroup(0);

            // Annulla gruppo provvisorio
            MultipleSelectState.getInstance().setGroup(null);

            //Nascondi menù a tendina
            StateController.getInstance().notifyGroupDeselected();


            // Rimuovi la versione decorata dalla vista (cioè dallo stato attuale)
            StateController.getInstance().removeShape(s,v);

            // Crea la versione "base" della view senza decorator
            ShapeView baseView = v.undecorate();

            // Aggiungi di nuovo la versione base alla vista
            StateController.getInstance().addShape(s,baseView);

        }
    }

    @Override
    public void handleDelete(KeyEvent event, Map<Shape,ShapeView> map) {
        //Nulla
    }

    @Override
    public void handleColorChanged(Color currentStroke, Color currentFill) {
        this.currentStroke = currentStroke;
        this.currentFill = currentFill;
    }

    @Override
    public void handleKeyTyped(KeyEvent event, Map<Shape, ShapeView> map) {

    }

    @Override
    public void handleChangeFontColor(Color currentFontColor) {

    }

    @Override
    public void handleChangeFontName(String currentFontName) {

    }

    @Override
    public void handleChangeFontSize(int currentFontSize) {

    }
}
