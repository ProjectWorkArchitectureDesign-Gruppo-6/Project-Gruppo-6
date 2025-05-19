package projectworkgroup6.State;

import javafx.scene.input.KeyEvent;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;

import java.util.List;

// Nello stato Seleziona, ci occupiamo della modifica delle figure presenti nel Canvas.

public class MultipleSelectState implements CanvasState {

    // --- Singleton classico ---
    private static MultipleSelectState instance;

    private MultipleSelectState() {
        // Costruttore privato per Singleton
    }

    public static MultipleSelectState getInstance() {
        if (instance == null) {
            instance = new MultipleSelectState();
        }
        return instance;
    }

    private List<Shape> shapes;

    @Override
    public void handleClick(double x, double y, List<Shape> shapes) {
        // TODO: implementare selezione della shape cliccata
        System.out.println("Hai cliccato in modalità SELECT su: " + x + ", " + y);
        for (Shape s : shapes) {
            if (s.contains(x, y)) {
                if(s.isSelected()){
                    s.setSelected(false);
                    s.getShapebase().setSelected(false);
                    StateController.getInstance().removeShape(s);
                    StateController.getInstance().addShape(s.getShapebase());
                    break;
                } else{
                    s.setSelected(true);
                    StateController.getInstance().removeShape(s);
                    StateController.getInstance().addShape(new SelectedDecorator(s));
                    break; // se vuoi selezione singola
                }

            }
        }


    }

    @Override
    public void handleMoveClick(double x, double y) {

        //System.out.println("Da definire");
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
    public void recoverShapes(List<Shape> shapes) {

    }

    @Override
    public void handleDelete(KeyEvent event, List<Shape> shapes) {
        // Per dopo
    }
}
