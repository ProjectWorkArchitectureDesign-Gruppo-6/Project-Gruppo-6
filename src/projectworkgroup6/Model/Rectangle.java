package projectworkgroup6.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rectangle extends Shape {
    private double width, height;

    // Per serializzazione
    public Rectangle() {
        super(0, 0, false, new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 0,0);
    }

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



    public Rectangle(double x, double y, boolean selected, double width, double height, ColorModel border, ColorModel fill, int layer, int group) {
        super(x, y, selected, border, fill, layer, group);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }



    @Override
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }


    @Override
    public void resize(double factor, double dx, double dy) {
        width *= factor;
        height *= factor;

    }

    @Override
    public boolean contains(double x, double y) {
        double left = this.getX() - width/2;
        double top = this.getY() - height/2;
        return x >= left && x <= left + width && y >= top && y <= top + height;
    }

    @Override
    public String type() {
        return "Rectangle";
    }
}