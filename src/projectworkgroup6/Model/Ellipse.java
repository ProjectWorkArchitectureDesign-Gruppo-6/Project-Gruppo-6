package projectworkgroup6.Model;

import javafx.scene.canvas.GraphicsContext;

public class Ellipse extends Shape {
    private double diam1, diam2;



    public Ellipse(double x, double y, boolean selected, double diam1, double diam2) {
        super(x,y,selected);
        this.diam1 = diam1;
        this.diam2 = diam2;
    }

    @Override
    public double getDim1() {
        return diam1;
    }

    @Override
    public double getDim2() {
        return diam2;
    }

    @Override
    public double getXc() {
        return this.getX() - diam1/2;
    }

    @Override
    public double getYc() {
        return this.getY() - diam2/2;
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
    public boolean contains(double x, double y) {
        double left = this.getX() - diam1 / 2;
        double top = this.getY() - diam2 / 2;
        return x >= left && x <= left + diam1 && y >= top && y <= top + diam2;
    }
}
