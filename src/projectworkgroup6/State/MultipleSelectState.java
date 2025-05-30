package projectworkgroup6.State;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import java.util.Map;

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

    public void handleClick(MouseEvent e,double x, double y, Map<Shape, ShapeView> map) {
        // Da gestire
    }

    /*

    @Override
    public void handleClick(double x, double y, Map<Shape, ShapeView> map) {
        // TODO: implementare selezione della shape cliccata
        System.out.println("Hai cliccato in modalità SELECT su: " + x + ", " + y);
        for (Shape s : map.keySet()) {
            if (s.contains(x, y)) {
                if(s.isSelected()){
                    s.setSelected(false);
                    ShapeView selectedShape = map.get(s);
                    StateController.getInstance().removeShape(s, selectedShape);
                    ShapeView baseShape = selectedShape.get();
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

     */

    @Override
    public void handlePression(double x, double y) {

        //System.out.println("Da definire");
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

    }

    @Override
    public void handleDelete(KeyEvent event, Map<Shape,ShapeView> map) {
        // Per dopo
    }

    @Override
    public void handleColorChanged(Color currentStroke, Color currentFill) {
        // Da implementare
    }

    @Override
    public void handleKeyTyped(KeyEvent event, Map<Shape, ShapeView> map) {

    }
}
