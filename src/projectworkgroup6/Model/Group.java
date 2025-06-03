package projectworkgroup6.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group extends Shape{

    // Per serializzazione
    public Group() {
        super(0, 0, false,0,0, new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 0,0);
    }


    private List<Shape> shapes;

    public Group(List<Shape> shapes, double x, double y, boolean selected, double width, double height, ColorModel border, ColorModel fill, int layer, int group) {
        super(x, y, selected, width, height, border, fill, layer, group);
        this.shapes = shapes;
    }


    @Override
    public double getDim1() {
        return width;
    }

    @Override
    public double getDim2() {
        return height;
    }

    @Override
    public double getXc() {
        return x - width/2;
    }

    @Override
    public double getYc() {
        return y - height/2;
    }

    @Override
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
        for (Shape s : shapes){
            s.move(dx, dy);
        }
    }

    /*
    @Override
    public void resize(double factor, double oldX, double oldY) {
        double oldW = width;
        double oldH = height;


        // Applico la nuova dimensione al gruppo (cornice)
        width *= factor;
        height *= factor;


        // Ora ho tutto per applicare la trasformazione alle shape figlie
        resizeProportional(oldX, oldY, oldW, oldH);
    }

    private void resizeProportional(double oldX, double oldY, double oldW, double oldH) {
        double newX = getX(); // Il nuovo centro già calcolato in base allo spostamento settato nello state
        double newY = getY();
        double newW = getDim1(); // width
        double newH = getDim2(); // height

        for (Shape s : shapes) {
            // Posizione relativa del centro della shape, valore percentuale di area occupata
            //Calcoli quanto lontano si trova la shape dal centro del gruppo, in percentuale rispetto alla larghezza e altezza del gruppo.
            double relX = (s.getX() - oldX) / oldW;
            double relY = (s.getY() - oldY) / oldH;

            // Nuova posizione proporzionale
            // Con le nuove dimensioni e il nuovo centro, voglio che la shape rimanga alla stessa distanza iniziale dal centro rispetto alla larghezza e all'altezza del gruppo
            double newShapeX = newX + relX * newW;
            double newShapeY = newY + relY * newH;

            // Fattori di scala
            double scaleX = newW / oldW;
            double scaleY = newH / oldH;
            double scale = Math.min(scaleX, scaleY);

            // Salva centro della shape vecchio in caso di riesecuzione su gruppi annidati
            double nestedOldX = s.getX();
            double nestedOldY = s.getY();

            // Applica trasformazione
            s.setX(newShapeX);
            s.setY(newShapeY);
            s.resize(scale,nestedOldX,nestedOldY);
        }
    }

     */

    @Override
    public void resize(double factorX, double factorY, double oldX, double oldY) {
        double oldW = width;
        double oldH = height;


        // Applico la nuova dimensione al gruppo (cornice)
        width *= factorX;
        height *= factorY;


        // Ora ho tutto per applicare la trasformazione alle shape figlie
        resizeProportional(oldX, oldY, oldW, oldH);
    }

    private void resizeProportional(double oldX, double oldY, double oldW, double oldH) {
        double newX = getX(); // Il nuovo centro già calcolato in base allo spostamento settato nello state
        double newY = getY();
        double newW = getDim1(); // width
        double newH = getDim2(); // height

        for (Shape s : shapes) {
            // Posizione relativa del centro della shape, valore percentuale di area occupata
            //Calcoli quanto lontano si trova la shape dal centro del gruppo, in percentuale rispetto alla larghezza e altezza del gruppo.
            double relX = (s.getX() - oldX) / oldW;
            double relY = (s.getY() - oldY) / oldH;


            // Nuova posizione proporzionale
            // Con le nuove dimensioni e il nuovo centro, voglio che la shape rimanga alla stessa distanza iniziale dal centro rispetto alla larghezza e all'altezza del gruppo
            double newShapeX = newX + relX*newW;
            double newShapeY = newY + relY*newH;

            // Fattori di scala
            double scaleX = newW / oldW;
            double scaleY = newH / oldH;


            // Salva centro della shape vecchio in caso di riesecuzione su gruppi annidati
            double nestedOldX = s.getX();
            double nestedOldY = s.getY();

            // Applica trasformazione
            s.setX(newShapeX);
            s.setY(newShapeY);
            s.resize(scaleX, scaleY, nestedOldX,nestedOldY);
        }
    }


    @Override
    public boolean contains(double x, double y) {
        return shapes.stream().anyMatch(s -> s.contains(x,y));
    }

    @Override
    public String type() {
        return "Group";
    }

    @Override
    public Shape cloneAt(double x, double y, int layer) {

        List<Shape> cloned = new ArrayList<>();
        for(Shape s : shapes){
            cloned.add(s.cloneAt(x-this.getX() + s.getX(),y-this.getY()+s.getY(),layer));
        }
        return new Group(cloned,x,y,false,this.width,this.height,this.border,this.fill,layer,this.group);
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
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

    public void add(Shape shape){
        this.shapes.add(shape);
    }

    public void remove(Shape shape){
        this.shapes.remove(shape);
    }

    @Override
    public void setBorder(ColorModel border) {
        for(Shape s : shapes){
            s.setBorder(border);
        }
    }

    @Override
    public void setFill(ColorModel fill) {
        for(Shape s : shapes){
            s.setFill(fill);
        }
    }


}
