package projectworkgroup6.Command;

import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;

public class MoveCommand implements Command {
    private Shape shape;
    private double dx, dy; // spostamento totale

    private StateController stateController;

    public MoveCommand(Shape shape) {
        this.shape = shape;
        this.stateController = StateController.getInstance();
    }

    public void accumulate(double deltaX, double deltaY) {
        this.dx += deltaX;
        this.dy += deltaY;
    }

    @Override
    public void execute() {
        shape.move(dx, dy);
    }

    @Override
    public void undo() {
        shape.move(-dx, -dy);
        }

}

