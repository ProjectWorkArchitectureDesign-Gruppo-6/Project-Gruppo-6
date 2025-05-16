package projectworkgroup6.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle implements Shape {
    private double x, y, width, height;

    private Color color;

    public Rectangle(double x, double y, Color color, double width, double height) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.strokeRect(x - width / 2, y - height / 2, width, height);

    }

    @Override
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void resize(double factor) {
        width *= factor;
        height *= factor;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
