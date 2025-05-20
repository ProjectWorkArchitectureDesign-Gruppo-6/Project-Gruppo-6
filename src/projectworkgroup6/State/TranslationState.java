package projectworkgroup6.State;

import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.MoveCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import java.util.Map;

public class TranslationState implements CanvasState{
    private final SelectedDecorator shapeView;
    private double lastX;
    private double lastY;

    private MoveCommand currentMoveCommand;

    public TranslationState(SelectedDecorator shapeView) {
        this.shapeView = shapeView;
    }

    @Override
    public void handleClick(double x, double y, Map<Shape, ShapeView> map) {
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

        Shape shape = shapeView.getShape();

        StateController.getInstance().removeShape(shape,shapeView); // rimuovo la shape dalla posizione iniziale

        double dx = x - lastX;
        double dy = y - lastY;

        currentMoveCommand.microstep(dx,dy);// spostamento della figura
        CommandManager.getInstance().executeCommand(currentMoveCommand);
        currentMoveCommand.accumulate(dx, dy);

        lastX = x;
        lastY = y; // salvataggio delle coordinate ottenute in base allo spostamento

        StateController.getInstance().addShape(shape, shapeView); // aggiungo shape alla posizione nuova

    }

    @Override
    public void handleMouseReleased(double x, double y) {
        StateController.getInstance().setState(SingleSelectState.getInstance());
    }

    @Override
    public void recoverShapes(Map<Shape, ShapeView> map) {
        // Non deve fare nulla
    }

    @Override
    public void handleDelete(KeyEvent event, Map<Shape, ShapeView> map) {
        // Poi
    }

    @Override
    public void handleColorChanged(Color currentStroke) {
        // Nulla
    }

    public void setMoveCommand(MoveCommand moveCommand) {
        this.currentMoveCommand = moveCommand;
    }
}
