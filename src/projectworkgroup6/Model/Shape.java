package projectworkgroup6.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {

    protected double x, y;

    public Shape(double x, double y, boolean selected) {
        this.x = x;
        this.y = y;
        this.selected = selected;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public abstract double getDim1();
    public abstract double getDim2();


    public abstract double getXc();
    public abstract double getYc();
    protected boolean selected = false;

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public abstract void draw(GraphicsContext gc);
    public abstract void move(double dx, double dy);
    public abstract void resize(double scaleFactor);
    public abstract void setColor(Color color);

    public abstract boolean contains(double x, double y);

    public Shape getShapebase() {
        return this;
    }
}
