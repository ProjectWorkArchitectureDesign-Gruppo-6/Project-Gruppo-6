package projectworkgroup6.State;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.DeleteCommand;
import projectworkgroup6.Command.MoveCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;

import java.util.List;

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

    private List<Shape> shapes;
    private SelectedDecorator selectedShape = null;


    @Override
    public void handleClick(double x, double y, List<Shape> shapes) {
        System.out.println("Hai cliccato in modalità SELECT su: " + x + ", " + y);

        for (Shape s : shapes) {
            if (s.contains(x, y)) {
                if (s.isSelected()) {
                    deselectShape(s);
                    return;
                }

                // Deseleziona eventuale altra shape selezionata
                shapes.stream()
                        .filter(Shape::isSelected)
                        .findFirst()
                        .ifPresent(this::deselectShape);

                selectShape(s);
                return;
            }
        }

        // Click a vuoto → nessuna azione oltre la stampa
    }

    private void deselectShape(Shape s) {
        s.setSelected(false);
        s.getShapebase().setSelected(false);
        StateController.getInstance().removeShape(s);
        StateController.getInstance().addShape(s.getShapebase());
        selectedShape = null;
    }

    private void selectShape(Shape s) {
        s.setSelected(true);
        StateController.getInstance().removeShape(s);
        selectedShape = new SelectedDecorator(s);
        StateController.getInstance().addShape(selectedShape);
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
                ts.setMoveCommand(new MoveCommand(selectedShape));
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
    public void recoverShapes(List<Shape> shapes) {
        //Non deve fare nulla
    }

    @Override
    public void handleDelete(KeyEvent event, List<Shape> shapes) {
        if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE){
            for(Shape s : shapes){
                if (s == selectedShape) {
                    DeleteCommand cmd = new DeleteCommand(s);
                    CommandManager.getInstance().executeCommand(cmd);
                    selectedShape = null;
                    break;
                }
            }
        }
    }
}
