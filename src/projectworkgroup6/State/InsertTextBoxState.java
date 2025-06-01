package projectworkgroup6.State;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.InsertCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Factory.TextBoxCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Model.TextBox;
import projectworkgroup6.View.ShapeView;
import projectworkgroup6.View.TextBoxView;

import java.util.HashMap;
import java.util.Map;

//entro in questo stato dopo il click sul bottone casella di testo

public class InsertTextBoxState implements CanvasState {

    private final TextBoxCreator creator;
    private Color currentStroke;
    private Color currentFill = Color.WHITE;

    //impostazioni di default per il font
    private Color currentFontColor = Color.BLACK;

    private String currentFontFamily = "Arial";

    private int currentFontSize = 12;

    public InsertTextBoxState(TextBoxCreator creator) {
        this.creator = creator;
        this.currentStroke = Color.BLACK;
    }


    @Override
    public void handleClick(MouseEvent e,double x, double y, Map<Shape, ShapeView> map) {

        StateController sc = StateController.getInstance();



        creator.setFontFamily(sc.getFontFamily()); //mi prendo le impostazioni del testo dai pulsanti tramite lo state controller
        creator.setFontSize(sc.getFontSize());
        creator.setFontColor(ColorModel.fromColor(sc.getFontColor()));
        //setto una variabile da usare nel ciclo per la verifica del click su una shape oppure no
        boolean clickedOnShape = false;
        //Itero sulla mappa prendendo i valori di shape e shapeView corrispondenti
        for (Map.Entry<Shape, ShapeView> entry : map.entrySet()) {
            Shape shape = entry.getKey();
            ShapeView shapeView = entry.getValue();

            if (shape.contains(x, y) && shape instanceof TextBox) { //se il click avviene su una figura già sul canvas ed è un textBox
                clickedOnShape=true; //setto clickedOnShape a true

                shape.setEditing(true); //setto set editing

                StateController.getInstance().setState(new EditingTextState(shape, shapeView)); //entro nello stato di editing
                StateController.getInstance().redrawCanvas(); //ridisegno il canvas per visualizzare il rettangolo della casella

                break;
            }

        } if(!clickedOnShape){ //se invece non sto facendo click su un textBox mi comporto come un normale stato di inserimento


            creator.setText("");

            System.out.println("Sono nello stato di inserimento");

            //Converto colori in ColorModel passandogli quelli correnti
            ColorModel border = ColorModel.fromColor(currentStroke);
            ColorModel fill = ColorModel.fromColor(currentFill);
            ColorModel font = ColorModel.fromColor(currentFontColor);

            // Creo la Shape con le info necessarie
            Shape shape = creator.createShape(x, y, 100, 50,  border, fill, map.size() + 1, 0);

            // Creo la ShapeView
            ShapeView shapeView = creator.createShapeView(shape);

            // Decoratore bordo
            BorderDecorator shapeBorder = new BorderDecorator(shapeView, currentStroke);

            // Inserisco con comando undoable
            InsertCommand cmd = new InsertCommand(shape, shapeBorder);
            CommandManager.getInstance().executeCommand(cmd);

            //dopo aver inserito la nuova shape come textBox e passa allo stato di editing
            StateController.getInstance().setState(new EditingTextState(shape, shapeView));
        }
    }

    @Override
    public void handlePression(double x, double y) { /* non usato */ }

    @Override
    public void handlePressionRotate(double x, double y) {

    }

    @Override
    public void handleMouseDragged(double x, double y) { /* non usato */ }

    @Override
    public void handleMouseReleased(double x, double y) { /* non usato */ }

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
    public void handleDelete(KeyEvent event, Map<Shape, ShapeView> map) { /* niente */ }

    @Override
    public void handleColorChanged(Color currentStroke, Color currentFill) {
        this.currentStroke = currentStroke;
        this.currentFill=currentFill;
    }

    @Override
    public void handleKeyTyped(KeyEvent event, Map<Shape, ShapeView> map) { /* niente */ }

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
