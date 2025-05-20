package projectworkgroup6.State;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.DeleteCommand;
import projectworkgroup6.Command.MoveCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Nello stato Seleziona, ci occupiamo della modifica delle figure presenti nel Canvas.

public class SingleSelectState implements CanvasState {

    // --- Singleton classico ---
    private static SingleSelectState instance;

    private SingleSelectState() {
        // Costruttore privato per Singleton
    }

    public static SingleSelectState getInstance() {
        if (instance == null) {
            instance = new SingleSelectState();
        }
        return instance;
    }

    private SelectedDecorator selectedShape = null;


    @Override
    public void handleClick(double x, double y, Map<Shape, ShapeView> map) {
        System.out.println("Hai cliccato in modalità SELECT su: " + x + ", " + y);

        for (Shape s : map.keySet()) {
            if (s.contains(x, y)) {
                if (s.isSelected()) {
                    deselectShape(s);
                    return;
                }

                // Deseleziona eventuale altra shape selezionata
                deselectAll(map);

                selectShape(s, map);
                return;
            }
        }

        // Click a vuoto → nessuna azione oltre la stampa
    }

    private void deselectAll(Map<Shape, ShapeView> map) {
        Map<Shape, ShapeView> copy = new HashMap<Shape, ShapeView>(map);
        for (Map.Entry<Shape, ShapeView> entry : copy.entrySet()) {
            Shape s = entry.getKey();
            ShapeView v = entry.getValue();

            // Deseleziona logicamente
            s.setSelected(false);

            // Se la view è decorata (cioè è un SelectedDecorator), la sostituiamo
            if (v instanceof SelectedDecorator) {
                // Rimuovi la versione decorata dalla vista (cioè dallo stato attuale)
                StateController.getInstance().removeShape(s,v);

                // Crea la versione "base" della view senza decorator
                ShapeView baseView = ((SelectedDecorator) v).undecorate();

                // Aggiungi di nuovo la versione base alla vista
                StateController.getInstance().addShape(s,baseView);

            }
        }
    }


    private void deselectShape(Shape s) {
        s.setSelected(false);
        StateController.getInstance().removeShape(s, selectedShape);
        StateController.getInstance().addShape(s, selectedShape.undecorate());
        selectedShape = null;
    }

    private void selectShape(Shape s, Map<Shape, ShapeView> map) {
        s.setSelected(true);
        ShapeView baseShapeView = map.get(s);
        StateController.getInstance().removeShape(s, baseShapeView);
        selectedShape = new SelectedDecorator(baseShapeView);
        StateController.getInstance().addShape(s,selectedShape);
    }

    @Override
    public void handleMoveClick(double x, double y) {

        if (selectedShape != null) {
            double buttonX = selectedShape.getMoveButtonX();
            double buttonY = selectedShape.getMoveButtonY();

            double diameter = 20;


            boolean first = x >= buttonX  && x <= buttonX + diameter;
            boolean second = y >= buttonY && y <= buttonY + diameter;

            boolean isClicked = first && second;

            if (isClicked) {
                TranslationState ts = new TranslationState(selectedShape);
                ts.startDragging(x,y);
                ts.setMoveCommand(new MoveCommand(selectedShape.getShape()));
                StateController.getInstance().setState(ts);
            }

        }
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
        //Non deve fare nulla
    }

    @Override
    public void handleDelete(KeyEvent event, Map<Shape,ShapeView> map) {
        if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE){
            for(Shape s : map.keySet()){
                if (map.get(s) == selectedShape) {
                    DeleteCommand cmd = new DeleteCommand(s, map.get(s));
                    CommandManager.getInstance().executeCommand(cmd);
                    selectedShape = null;
                    break;
                }
            }
        }
    }

    @Override
    public void handleColorChanged(Color currentStroke) {
        // Da implementare
    }
}
