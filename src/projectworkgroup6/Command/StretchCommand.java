package projectworkgroup6.Command;

import projectworkgroup6.Model.Shape;

import java.util.ArrayList;
import java.util.List;

public class StretchCommand implements Command {

    private final Shape shape;

    // Backup iniziale
    private final double originalX;
    private final double originalY;
    private final double originalDim1;
    private final double originalDim2;

    // Accumulo step-by-step
    private final List<Double> dxList = new ArrayList<>();
    private final List<Double> dyList = new ArrayList<>();
    private final List<String> directionList = new ArrayList<>();

    public StretchCommand(Shape shape) {
        this.shape = shape;
        this.originalX = shape.getX();
        this.originalY = shape.getY();
        this.originalDim1 = shape.getDim1();
        this.originalDim2 = shape.getDim2();
    }

    public void accumulate(double dx, double dy, String direction) {
        dxList.add(dx);
        dyList.add(dy);
        directionList.add(direction);
    }

    @Override
    public void execute() {
        for (int i = 0; i < dxList.size(); i++) {
            shape.stretch(dxList.get(i), dyList.get(i), directionList.get(i));
        }
    }

    @Override
    public void undo() {
        // Ripeti le stesse azioni al contrario
        for (int i = dxList.size() - 1; i >= 0; i--) {
            shape.stretch(-dxList.get(i), -dyList.get(i), directionList.get(i));
        }
    }

    public void undoStretchOnly() {
        undo();
    }
}
