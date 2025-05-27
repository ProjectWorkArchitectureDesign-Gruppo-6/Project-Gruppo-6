package projectworkgroup6.Command;

import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
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
        //quando cancello la shape selezionata, faccio sparire il menu a tendina
        StateController.getInstance().notifyShapeDeselected();
        //mi serve la view relativa alla shape
        controller.removeShape(shape, StateController.getInstance().getMap().get(shape));
    }
}

