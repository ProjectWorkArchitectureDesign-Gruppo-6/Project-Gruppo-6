package projectworkgroup6.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.scene.canvas.GraphicsContext;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Line extends Shape {
    private double x2, y2;

    public Line() {
        super(0, 0, false,new ColorModel(0,0,0,1), null);
    }

    public Line(double x1, double y1, boolean selected, double x2, double y2, ColorModel border, ColorModel fill) {
        super(x1,y1,selected, border, fill);
        this.x2 = x2;
        this.y2 = y2;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    @Override
    public double getDim1() {
        return x2 - ((x2-x)/2);
    }

    @Override
    public double getDim2() {
        return y2 - ((y2-y)/2);
    }

    @Override
    public double getXc() {
        return this.getX() - ((x2-x)/2);
    }

    @Override
    public double getYc() {
        return this.getY() - ((y2-y)/2);
    }



    @Override
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
        x2 += dx;
        y2 += dy;
    }

    @Override
    public void resize(double factor) {
        x *= factor;
        y *= factor;
    }

    @Override
    public boolean contains(double x, double y) {

        double tollerance = 3.0; //Aggiunta per il click sulla linea

        // Verifico che il click sta nei confini della linea
        boolean first = x >= getXc() && y >= getYc();
        boolean second = x <= getDim1() && y <= getDim2();

        // Coefficienti per il calcolo della distanza punto retta su cui giace la linea
        double a = getYc()-getDim2();
        double b = getDim1()-getXc();
        double c = -getYc()*getDim1() + getXc()*getDim2();

        // Calcolo della distanza
        double distanza = (a*x+b*y+c)/(Math.sqrt(Math.pow(a,2) + Math.pow(b,2)));

        // Verifico che il click avviene sulla linea, considerando una tolleranza
        boolean onLine = distanza<=tollerance && distanza >= -tollerance;

        return first && second && onLine;
    }
}
