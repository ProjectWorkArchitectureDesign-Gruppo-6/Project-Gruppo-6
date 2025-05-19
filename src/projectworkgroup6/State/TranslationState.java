package projectworkgroup6.State;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.MoveCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;

import java.util.List;

public class TranslationState implements CanvasState{
    private final SelectedDecorator shape;
    private double lastX;
    private double lastY;

    private MoveCommand currentMoveCommand;

    public TranslationState(SelectedDecorator shape) {
        this.shape = shape;
    }

    @Override
    public void handleClick(double x, double y, List<Shape> shapes) {
        //System.out.println("Non definito");
    }

    @Override
    public void handleMoveClick(double x, double y) {
        //System.out.println("Non definito");
    }

    public void startDragging(double x, double y) {
        lastX = x;
        lastY = y;
    }

    @Override
    public void handleMouseDragged(double x, double y) { // va in esecuzione più volte durante lo spostamento

        StateController.getInstance().removeShape(shape); // rimuovo la shape dalla posizione iniziale

        double dx = x - lastX;
        double dy = y - lastY;

        currentMoveCommand.microstep(dx,dy);// spostamento della figura
        CommandManager.getInstance().executeCommand(currentMoveCommand);
        currentMoveCommand.accumulate(dx, dy);

        lastX = x;
        lastY = y; // salvataggio delle coordinate ottenute in base allo spostamento

        StateController.getInstance().addShape(shape); // aggiungo shape alla posizione nuova

    }

    @Override
    public void handleMouseReleased(double x, double y) {
        StateController.getInstance().setState(SingleSelectState.getInstance());
    }

    @Override
    public void recoverShapes(List<Shape> shapes) {
        // Non deve fare nulla
    }

    @Override
    public void handleDelete(KeyEvent event, List<Shape> shapes) {
        // Poi
    }

    public void setMoveCommand(MoveCommand moveCommand) {
        this.currentMoveCommand = moveCommand;
    }
}
