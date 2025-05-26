package projectworkgroup6.Command;

import javafx.scene.Node;

public class ZoomCommand implements Command {
    private final Node target;
    private final double factor;

    public ZoomCommand(Node target, double factor) {
        this.target = target;
        this.factor = factor;
    }

    @Override
    public void execute() {
        target.setScaleX(target.getScaleX() * factor);
        target.setScaleY(target.getScaleY() * factor);
    }

    @Override
    public void undo() {
        if (factor != 0) {
            target.setScaleX(target.getScaleX() / factor);
            target.setScaleY(target.getScaleY() / factor);
        }
    }
}