package projectworkgroup6.Command;

import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import javax.swing.plaf.nimbus.State;

public class ChangeFillCommand implements Command{

    Shape shape;
    ColorModel oldFill;
    ColorModel fill;

    public ChangeFillCommand(Shape shape, ColorModel fill) {
        this.shape = shape;
        this.oldFill = this.shape.getFill();
        this.fill = fill;
    }

    @Override
    public void execute() {
        shape.setFill(fill);
    }

    @Override
    public void undo() {
        shape.setFill(oldFill);
    }
}
