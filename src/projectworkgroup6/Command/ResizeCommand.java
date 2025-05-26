package projectworkgroup6.Command;

import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;

public class ResizeCommand implements Command{

    private Shape shape;
    private double factor; // ridimensionamento totale


    private StateController stateController;

    public ResizeCommand(Shape shape) {
        this.shape = shape;
        this.stateController = StateController.getInstance();
    }

    public void accumulate(double factor) {
        this.factor = factor;
    }

    @Override
    public void execute() {
        shape.resize(factor);
    }

    @Override
    public void undo() {
        shape.resize(1/factor);
    }
}
