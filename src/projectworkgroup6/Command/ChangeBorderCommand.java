package projectworkgroup6.Command;

import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import javax.swing.plaf.nimbus.State;

public class ChangeBorderCommand implements Command{

    Shape shape;
    Object oldBorder;
    ColorModel border;

    public ChangeBorderCommand(Shape shape, ColorModel border) {
        this.shape = shape;
        this.oldBorder = shape.getStroke();
        this.border = border;
    }

    @Override
    public void execute() {
        shape.setBorder(border);

    }

    @Override
    public void undo() {
        shape.setStroke(oldBorder);

    }
}
