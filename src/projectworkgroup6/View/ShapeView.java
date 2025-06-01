package projectworkgroup6.View;

import javafx.scene.canvas.GraphicsContext;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;

public abstract class ShapeView {
    protected Shape shape;
    protected int layer;

    public ShapeView(Shape shape) {
        this.shape = shape;
        this.layer = shape.getLayer();
    }

    public Shape getShape() {
        return shape;
    }


    public abstract void draw(GraphicsContext gc);

    public ShapeView undecorate(){
        return this;
    }

    public int getLayer() {
        return shape.getLayer();
    }


}
