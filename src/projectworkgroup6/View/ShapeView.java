package projectworkgroup6.View;

import javafx.scene.canvas.GraphicsContext;
import projectworkgroup6.Model.Shape;

public abstract class ShapeView {
    protected Shape shape;

    public ShapeView(Shape shape) {
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }


    public abstract void draw(GraphicsContext gc);

    public ShapeView undecorate(){
        return this;
    }

}
