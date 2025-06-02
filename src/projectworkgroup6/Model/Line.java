package projectworkgroup6.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Line extends Shape {
    private double x2, y2;

    public Line() {
        super(0, 0, false,new ColorModel(0,0,0,1), null, 0,0);
    }

    public Line(double x1, double y1, boolean selected, double x2, double y2, ColorModel border, ColorModel fill, int layer, int group) {
        super(x1,y1,selected, border, fill, layer, group);
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
    //"larghezza" line
    public double getDim1() {
        return x2 - ((x2-x)/2);
    }

    @Override
    //"lunghezza" linea
    public double getDim2() {
        return y2 - ((y2-y)/2);
    }

    @Override
    public void setDim1(double newWidth) {
        double cx = (x + x2) / 2;
        double half = newWidth / 2;

        double direction = (x2 >= x) ? 1 : -1; // mantieni la direzione originale
        x = cx - direction * half;
        x2 = cx + direction * half;
    }

    @Override
    public void setDim2(double newHeight) {
        double cy = (y + y2) / 2;
        double half = newHeight / 2;

        double direction = (y2 >= y) ? 1 : -1;
        y = cy - direction * half;
        y2 = cy + direction * half;
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
        public void resize(double factor, double dx, double dy) {
            // Calcola il centro della linea
            double centerX = (x + x2) / 2;
            double centerY = (y + y2) / 2;


        // Applico il fattore di scala solo al secondo punto
        x2 = x + dx * factor;
        y2 = y + dy * factor;
    }

    @Override
    public void stretch(double dx, double dy, String id) {
        if (id.matches("UP")) {
            x += dx;
            y += dy;
            }
            else if (id.matches("DOWN"))   {
            x += dx/2;
            y += dy/2;
                x2 += dx;
                y2 += dy;
            }
        }



    @Override
    public boolean contains(double x, double y) {

        double tollerance = 3.0; //Aggiunta per il click sulla linea
       //ho tolto first e second perchè da problemi quando faccio mirroring

        // Coefficienti per il calcolo della distanza punto retta su cui giace la linea
        double a = getYc()-getDim2();
        double b = getDim1()-getXc();
        double c = -getYc()*getDim1() + getXc()*getDim2();


        // Calcolo della distanza
        double distanza = Math.abs(a*x+b*y+c)/(Math.sqrt(Math.pow(a,2) + Math.pow(b,2)));

        // Verifico che il click avviene sulla linea, considerando una tolleranza
        boolean onLine = distanza<=tollerance && distanza >= -tollerance;

        return  onLine;
    }

    @Override
    public String type() {
        return "Line";
    }

    @Override
    public Shape cloneAt(double x, double y, int layer){
        return new Line(x, y, false, this.x2, this.y2, this.border, this.fill, layer, this.group);
    }

}
