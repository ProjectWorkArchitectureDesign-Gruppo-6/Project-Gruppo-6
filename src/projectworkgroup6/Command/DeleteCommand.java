package projectworkgroup6.Command;

import projectworkgroup6.Model.Shape;
import projectworkgroup6.Controller.StateController;

public class DeleteCommand implements Command {
    private Shape shape;
    private StateController stateController;

    public DeleteCommand(Shape shape) {
        this.shape = shape;
        this.stateController = StateController.getInstance();
    }

    @Override
    public void execute() {
        stateController.removeShape(shape);
    }

    @Override
    public void undo() {
        stateController.addShape(shape);
    }
}

