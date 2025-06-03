package projectworkgroup6.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rectangle extends Shape {


    // Per serializzazione
    public Rectangle() {
        super(0, 0, false, 0, 0, new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 0,0);
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
        super(x, y, selected, width, height, border, fill, layer, group);
    }





    @Override
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void resize(double factorX, double factorY, double dx, double dy) {


        width *= factorX;
        height *= factorY;


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

    @Override
    public Shape cloneAt(double x, double y, int layer){
        return new Rectangle(x, y, false, this.width, this.height, this.border, this.fill, layer, this.group);
    }

}