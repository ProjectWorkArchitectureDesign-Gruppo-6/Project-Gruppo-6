package projectworkgroup6.Command;

import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Group;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.GroupView;
import projectworkgroup6.View.ShapeView;

import java.util.List;
import java.util.Map;

public class InsertGroupCommand implements Command{

    private Group group;
    private List<Shape> shapes;
    private GroupView view;
    private List<ShapeView> views;
    Map<Shape, ShapeView> map;


    public InsertGroupCommand(Group group, GroupView view) {
        this.group = group;
        this.view = view;
        this.shapes = group.getShapes();
        this.views = view.getViews();
        this.map = StateController.getInstance().getMap();
    }

    @Override
    public void execute() {
        for(int i = 0; i < shapes.size(); i++){
            StateController.getInstance().removeShape(shapes.get(i),views.get(i));
        }
        StateController.getInstance().addShape(group,view);
        StateController.getInstance().addCurrentGroup();
    }

    @Override
    public void undo() { //ungroup
        StateController.getInstance().removeShape(group,map.get(group));
        for(int i = 0; i < shapes.size(); i++){
            StateController.getInstance().addShape(shapes.get(i),views.get(i));
        }
        StateController.getInstance().remCurrentGroup();
    }
}
