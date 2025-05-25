package projectworkgroup6.Decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projectworkgroup6.View.ShapeView;

public class FillDecorator extends ShapeView{
    private final ShapeView baseView;
    private final Color fillColor;

    public FillDecorator(ShapeView baseView, Color fillColor) {
        super(baseView.getShape()); // eredita la stessa shape
        this.baseView = baseView;
        this.fillColor = fillColor;
    }

    @Override
    public void draw(GraphicsContext gc) {

        // Applico bordo
        gc.setFill(fillColor);


        // Disegno originale
        baseView.draw(gc);

    }


    @Override
    public ShapeView undecorate() {
        return baseView;
    }
}
