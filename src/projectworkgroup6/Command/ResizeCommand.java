package projectworkgroup6.Command;

import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;

public class ResizeCommand implements Command{

    private Shape shape;
    private double factorX = 1; // ridimensionamento totale
    private double factorY = 1;

    private double centerX, centerY;

    private double lastX, lastY;


    private StateController stateController;

    public ResizeCommand(Shape shape) {
        this.shape = shape;
        this.centerX = shape.getX();
        this.centerY = shape.getY();
    }

    public void accumulate(double factorX, double factorY) {
        this.factorX *= factorX;
        this.factorY *= factorY;
    }

    @Override
    public void execute() {
        shape.resize(factorX,factorY,centerX,centerY);
    }

    @Override
    public void undo() {
        shape.setX(centerX);
        shape.setY(centerY);
        shape.resize(1/factorX,1/factorY,lastX,lastY);
    }

    public void undofactor(double oldX, double oldY){
        this.lastX = oldX;
        this.lastY = oldY;
        shape.resize(1/factorX,1/factorY,oldX,oldY);
    }

}
