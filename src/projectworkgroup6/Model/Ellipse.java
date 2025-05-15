package projectworkgroup6.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ellipse implements Shape {
    private double x, y, diam1, diam2;
    private Color color = Color.BLACK;

    public Ellipse(double x, double y, double diam1, double diam2) {
        this.x = x;
        this.y = y;
        this.diam1 = diam1;
        this.diam2 = diam2;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.strokeOval(x, y, diam1, diam2);
    }

    @Override
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void resize(double factor) {
        diam1 *= factor;
        diam2 *= factor;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
