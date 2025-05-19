package projectworkgroup6.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Shape {
    private double width, height;

    @Override
    public double getDim1() {
        return width;
    }

    @Override
    public double getDim2() {
        return height;
    }

    public double getXc(){
        return this.getX() - width/2;
    } // Xc è la coordinata del lato sinistro
    public double getYc(){
        return this.getY() - height/2;
    } // Yc è la coordinata del lato superiore

    private Color color;

    public Rectangle(double x, double y, boolean selected, Color color, double width, double height) {
        super(x, y, selected);
        this.color = color;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.strokeRect(this.getXc(), this.getYc(), width, height);

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

    @Override
    public boolean contains(double x, double y) {
        double left = this.getX() - width/2;
        double top = this.getY() - height/2;
        return x >= left && x <= left + width && y >= top && y <= top + height;
    }
}
