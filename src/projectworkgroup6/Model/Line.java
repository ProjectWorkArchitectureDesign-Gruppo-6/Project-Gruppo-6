package projectworkgroup6.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Line extends Shape {
    private double x1, y1, x2, y2;

    public Line() {
        super(0, 0, false,0,0,new ColorModel(0,0,0,1), null, 0,0);
    }

    public Line(double x1, double y1, boolean selected, double width, double height, ColorModel border, ColorModel fill, int layer, int group) {
        super(x1,y1,selected, width, height, border, fill, layer, group);
    }

    public double getX2() { // vertice in basso a destra
        return this.getXc() + width;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() { // vertice in basso a destra
        return this.getYc() + height;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    @Override
    public double getDim1() { // lunghezza del rettangolo che circoscrive la retta
        return width;
    }

    @Override
    public double getDim2() { // altezza del rettangolo che circoscrive la retta
        return height;
    }

    @Override
    public double getXc() { // vertice in alto a sinistra
        return this.getX() - width / 2;
    }

    @Override
    public double getYc() { // vertice in alto a sinistra
        return this.getY() - height/2;
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
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
        x2 += dx;
        y2 += dy;
    }


    @Override
    public void resize(double factorX, double factorY, double dx, double dy) {
        // Punto iniziale invariato (x, y), punto finale cambia
        double newWidth = width * factorX;
        double newHeight = height * factorY;

        this.width = newWidth;
        this.height = newHeight;

        // Aggiorna anche x2 e y2 per coerenza
        this.x2 = this.getX() + newWidth;
        this.y2 = this.getY() + newHeight;
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
        double a = getYc()-getY2();
        double b = getX2()-getXc();
        double c = -getYc()*getX2() + getXc()*getY2();


        // Calcolo della distanza
        double distanza = Math.abs(a*x+b*y+c)/(Math.sqrt(Math.pow(a,2) + Math.pow(b,2)));

        // Verifico che il click avviene sulla linea, considerando una tolleranza

        return distanza<=tollerance && distanza >= -tollerance;

        //return  onLine;
    }

    @Override
    public String type() {
        return "Line";
    }

    @Override
    public Shape cloneAt(double x, double y, int layer){
        return new Line(x, y, false, this.x2, this.y2, this.border, this.fill, layer, this.group);
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public void setY1(double y1){
        this.y1=y1;
    }
}
