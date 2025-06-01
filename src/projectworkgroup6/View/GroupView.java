package projectworkgroup6.View;

import javafx.scene.canvas.GraphicsContext;
import projectworkgroup6.Model.Group;

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

        for(ShapeView v : views){
            v.draw(gc);
        }
    }
}
