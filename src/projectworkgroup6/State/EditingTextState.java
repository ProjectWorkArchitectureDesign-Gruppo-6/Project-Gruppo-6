package projectworkgroup6.State;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Factory.TextBoxCreator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Model.TextBox;
import projectworkgroup6.View.ShapeView;
import projectworkgroup6.View.TextBoxView;

import java.util.Map;

public class EditingTextState implements CanvasState {

    private Shape textBox;
    private ShapeView textBoxView;

    private String oldText;

    public EditingTextState(Shape textBox, ShapeView textBoxView) {
        this.textBox = textBox;
        this.textBoxView = textBoxView;
        this.oldText = textBox.getText();
    }

    @Override
    public void handleClick(MouseEvent e,double x, double y, Map<Shape, ShapeView> map) {
        //se faccio click fuori dalla casella di testo
        if(!textBox.contains(x, y)){
            StateController.getInstance().setState(SingleSelectState.getInstance()); //passo al singleSelectState
            textBox.setEditing(false); //metto setEditing a false per il disegno del rettangolo
        }
        StateController.getInstance().redrawCanvas(); //ridisegno il canvas per togliere il rettangolo

    }

    @Override
    public void handleKeyTyped(KeyEvent event, Map<Shape, ShapeView> map) {
        System.out.println("Sono nello stato di editing");

        // Setto il focus al canvas per gestire gli input da tastiera solo nella modalità editing
        StateController.getInstance().requestCanvasFocus();

        //prendo carattere per carattere da tastiera
        String character = event.getCharacter();
        //aggiorno il currentText per inserirlo nel modello del textBox corrente
        String currentText = textBox.getText();

        //creo una nuova stringa
        String newText;

        if (character.equals("\b")) { //carattere backspace
            if (!currentText.isEmpty()) { //se il testo c'è
                newText = currentText.substring(0, currentText.length() - 1); //cancella
            } else {
                newText = ""; //altrimenti non cancella nulla
            }
        } else {
            //Se la casella di testo è vuota
            if (currentText == null || currentText.isEmpty()) {
                newText = character; //il primo carattere corrisponde a netext
            } else {
                newText = currentText + character; //altrimenti si fa un append del current text(ciò che già c'era nella casella) con i nuovi caratteri
            }
        }

        textBox.setText(newText); //aggiorni il testo totale

        // Aggiorna oldText per undo/redo
        oldText = newText;

        event.consume();

        // Ridisegno il canvas per aggiornare la vista con il nuovo carattere
        StateController.getInstance().redrawCanvas();
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


    @Override
    public void handlePression(double x, double y) {}

    @Override
    public void handlePressionRotate(double x, double y) {
    }

    @Override
    public void handleMouseDragged(double x, double y) {}

    @Override
    public void handleMouseReleased(double x, double y) {}

    @Override
    public void recoverShapes(Map<Shape, ShapeView> map) {}

    @Override
    public void handleDelete(KeyEvent event, Map<Shape, ShapeView> map) {}

    @Override
    public void handleColorChanged(Color currentStroke, Color currentFill) {}


}