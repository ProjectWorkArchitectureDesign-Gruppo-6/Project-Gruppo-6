package projectworkgroup6.Decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projectworkgroup6.View.ShapeView;

public class BorderDecorator extends ShapeView{
    private final ShapeView baseView;
    private final Color borderColor;

    public BorderDecorator(ShapeView baseView, Color borderColor) {
        super(baseView.getShape()); // eredita la stessa shape
        this.baseView = baseView;
        this.borderColor = borderColor;
    }

    @Override
    public void draw(GraphicsContext gc) {

        // Applico bordo
        gc.setStroke(borderColor);
        gc.setLineWidth(3);

        // Disegno originale
        baseView.draw(gc);

    }


    @Override
    public ShapeView undecorate() {
        return baseView;
    }
}
