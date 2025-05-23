package projectworkgroup6.Command;

import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

public class InsertCommand implements Command {

    private final Shape shape;
    private final ShapeView view;
    private final StateController controller;

    public InsertCommand(Shape shape, ShapeView view) {
        this.shape = shape;
        this.view = view;
        this.controller = StateController.getInstance();
    }

    @Override
    public void execute() {
        controller.addShape(shape, view);
    }

    @Override
    public void undo() {
        controller.removeShape(shape, view);
    }
}

