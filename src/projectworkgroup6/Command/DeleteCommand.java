package projectworkgroup6.Command;

import projectworkgroup6.Model.Shape;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.View.ShapeView;

public class DeleteCommand implements Command {
    private Shape shape;
    private ShapeView shapeView;
    private StateController stateController;

    public DeleteCommand(Shape shape, ShapeView shapeView) {
        this.shape = shape;
        this.shapeView = shapeView;
        this.stateController = StateController.getInstance();
    }

    @Override
    public void execute() {
        stateController.removeShape(shape, shapeView);
    }

    @Override
    public void undo() {
        stateController.addShape(shape, shapeView.undecorate());
    }
}

