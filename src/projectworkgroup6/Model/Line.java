package projectworkgroup6.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line implements Shape {
    private double x1, y1, x2, y2;
    private Color color = Color.BLACK;

    public Line(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.strokeLine(x1, y1, x2, y2);
    }

    @Override
    public void move(double dx, double dy) {
        x1 += dx;
        y1 += dy;
    }

    @Override
    public void resize(double factor) {
        x1 *= factor;
        y1 *= factor;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
