package projectworkgroup6.Command;

import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import javax.swing.plaf.nimbus.State;

public class ChangeBorderCommand implements Command{

    Shape shape;
    ColorModel oldBorder;
    ColorModel border;

    public ChangeBorderCommand(Shape shape, ColorModel border) {
        this.shape = shape;
        this.oldBorder = this.shape.getBorder();
        this.border = border;
    }

    @Override
    public void execute() {
        shape.setBorder(border);
        StateController.getInstance().redrawCanvas();
    }

    @Override
    public void undo() {
        shape.setBorder(oldBorder);
        StateController.getInstance().redrawCanvas();
    }
}
