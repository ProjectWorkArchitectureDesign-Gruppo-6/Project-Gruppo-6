package projectworkgroup6.Command;

import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;

public class ResizeCommand implements Command{

    private Shape shape;
    private double factor = 1; // ridimensionamento totale

    private double centerX, centerY;


    private StateController stateController;

    public ResizeCommand(Shape shape) {
        this.shape = shape;
        this.centerX = shape.getX();
        this.centerY = shape.getY();
    }

    public void accumulate(double factor) {
        this.factor *= factor;
    }

    @Override
    public void execute() {
        shape.resize(factor);
    }

    @Override
    public void undo() {
        shape.resize(1/factor);
        shape.setX(centerX);
        shape.setY(centerY);
    }

    public void undofactor(){
        shape.resize(1/factor);
    }
}
