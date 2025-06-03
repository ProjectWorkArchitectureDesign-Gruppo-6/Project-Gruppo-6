package projectworkgroup6.Command;

import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Group;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.GroupView;
import projectworkgroup6.View.ShapeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UngroupCommand implements Command{

    private Group group;
    private List<Shape> shapes;
    private GroupView view;
    private List<ShapeView> views;
    Map<Shape, ShapeView> map;


    public UngroupCommand(Group group, GroupView view) {
        this.group = group;
        this.view = view;
        this.shapes = group.getShapes();
        this.views = view.getViews();
        this.map = StateController.getInstance().getMap();
    }

    private final List<Double> oldXs = new ArrayList<>();
    private final List<Double> oldYs = new ArrayList<>();
    private final List<Double> oldRotations = new ArrayList<>();


    @Override
    public void execute() {
        double groupAngle = Math.toRadians(group.getRotation());
        double cx = group.getX();
        double cy = group.getY();

        // Applica rotazione del gruppo
        for (Shape s : shapes) {
            // Salva stato originale
            oldXs.add(s.getX());
            oldYs.add(s.getY());
            oldRotations.add(s.getRotation());

            // Offset rispetto al centro del gruppo
            double dx = s.getX() - cx;
            double dy = s.getY() - cy;

            // Rotazione attorno al centro del gruppo
            double rotatedX = dx * Math.cos(groupAngle) - dy * Math.sin(groupAngle);
            double rotatedY = dx * Math.sin(groupAngle) + dy * Math.cos(groupAngle);

            // Nuova posizione assoluta
            s.setX(cx + rotatedX);
            s.setY(cy + rotatedY);

            // Nuova rotazione: somma con quella del gruppo
            s.setRotation(s.getRotation() + group.getRotation());
        }

        StateController.getInstance().removeShape(group,map.get(group));
        for(int i = 0; i < shapes.size(); i++){
            StateController.getInstance().addShape(shapes.get(i),views.get(i));
        }
        StateController.getInstance().remCurrentGroup();

    }

    @Override
    public void undo() {
        for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).setX(oldXs.get(i));
            shapes.get(i).setY(oldYs.get(i));
            shapes.get(i).setRotation(oldRotations.get(i));
            StateController.getInstance().removeShape(shapes.get(i), views.get(i));
        }
        StateController.getInstance().addShape(group,view);
        StateController.getInstance().addCurrentGroup();

    }
}
