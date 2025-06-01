package projectworkgroup6.View;

import javafx.scene.canvas.GraphicsContext;
import projectworkgroup6.Model.Group;
import projectworkgroup6.Model.Rectangle;

import java.util.List;

public class GroupView extends ShapeView{

    private List<ShapeView> views;

    public GroupView(Group shape, List<ShapeView> views){
        super(shape);
        this.views = views;
    }

    public List<ShapeView> getViews() {
        return views;
    }

    public void setViews(List<ShapeView> views) {
        this.views = views;
    }

    @Override
    public void draw(GraphicsContext gc) {

        Group group = (Group) shape;

        double centerX = group.getXc() + group.getDim1() / 2.0;
        double centerY = group.getYc() + group.getDim2() / 2.0;
        double angle = group.getRotation();

        gc.save();

        gc.translate(centerX, centerY);
        gc.rotate(angle);
        gc.translate(-centerX, -centerY);

        for(ShapeView v : views){
            v.draw(gc);
        }

        gc.restore();


    }
}
