package projectworkgroup6.State;

import javafx.scene.input.KeyEvent;
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

    private final ShapeCreator creator;
    private Color currentStroke;
    private Color currentFill = Color.WHITE;

    //impostazioni di default per il font
    private Color currentFontColor = Color.BLACK;

    private String currentFontFamily = "Arial";

    private int currentFontSize = 12;

    public InsertTextBoxState(ShapeCreator creator) {
        this.creator = creator;
        this.currentStroke = Color.BLACK;
    }


    @Override
    public void handleClick(double x, double y, Map<Shape, ShapeView> map) {

        //setto una variabile da usare nel ciclo per la verifica del click su una shape oppure no
        boolean clickedOnShape = false;
        //Itero sulla mappa prendendo i valori di shape e shapeView corrispondenti
        for (Map.Entry<Shape, ShapeView> entry : map.entrySet()) {
            Shape shape = entry.getKey();
            ShapeView shapeView = entry.getValue();

            /*non sono riuscita ad evitare il controllo inatanceOf perchè per le altre figure non può entrare in modalità editing se fa click*/
            if (shape.contains(x, y) && shape instanceof TextBox) { //se il click avviene su una figura già sul canvas ed è un textBox
                clickedOnShape=true; //setto clickedOnShape a true

                shape.setEditing(true); //setto set editing

                StateController.getInstance().setState(new EditingTextState(shape, shapeView)); //entro nello stato di editing
                StateController.getInstance().redrawCanvas(); //ridisegno il canvas per visualizzare il rettangolo della casella

                break;
            }

        } if(!clickedOnShape){ //se invece non sto facendo click su un textBox mi comporto come un normale stato di inserimento
            StateController sc = StateController.getInstance();

            TextBoxCreator textBoxCreator = (TextBoxCreator) creator;

            textBoxCreator.setFontFamily(sc.getFontFamily()); //mi prendo le impostazioni del testo dai pulsanti tramite lo state controller
            textBoxCreator.setFontSize(sc.getFontSize());
            textBoxCreator.setFontColor(ColorModel.fromColor(sc.getFontColor()));

            textBoxCreator.setText("");

            System.out.println("Sono nello stato di inserimento");

            //Converto colori in ColorModel passandogli quelli correnti
            ColorModel border = ColorModel.fromColor(currentStroke);
            ColorModel fill = ColorModel.fromColor(currentFill);
            ColorModel font = ColorModel.fromColor(currentFontColor);

            // Creo la Shape con le info necessarie
            Shape shape = creator.createShape(x, y, border, fill);

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
    public void handleMouseDragged(double x, double y) { /* non usato */ }

    @Override
    public void handleMouseReleased(double x, double y) { /* non usato */ }

    @Override
    public void recoverShapes(Map<Shape, ShapeView> map) {

        Map<Shape, ShapeView> copy = new HashMap<>(map);
        for (Map.Entry<Shape, ShapeView> entry : copy.entrySet()) {
            Shape s = entry.getKey();
            ShapeView v = entry.getValue();
            s.setSelected(false);
            if (v instanceof SelectedDecorator) {
                StateController.getInstance().removeShape(s, v);
                ShapeView baseView = (v).undecorate();
                StateController.getInstance().addShape(s, baseView);
            }
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
}
