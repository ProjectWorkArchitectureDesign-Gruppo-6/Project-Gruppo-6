package projectworkgroup6.Model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public void setDim1(double dim1) {

    }

    @Override
    public void setDim2(double dim2) {

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
    public void stretch(double dx, double dy, String id) {


        this.x += dx / 2;
        this.y += dy / 2;

        if (id.equals("RIGHT") || id.equals("UP")) {
            this.width = Math.max(width + dx + dy , 1);
            this.height = Math.max(height - dx -dy, 1);

        }
        if (id.equals("LEFT") || id.equals("DOWN")) {
            this.width = Math.max(width -dx -dy,1);
            this.height =  Math.max(height +dx +dy,1);
        }

        for(Shape s : shapes){
            s.stretch(dx,dy,id);
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
        if(shapes != null){
            for(Shape s : shapes){
                s.setBorder(border);
            }
        }

    }



    @Override
    public void setFill(ColorModel fill) {
        if(shapes != null){
            for(Shape s : shapes){
                s.setFill(fill);
            }
        }

    }



    // Usato nel command per polimorfismo
    public Object getStroke() {
        Map<Shape, ColorModel> map = new HashMap<>();
        for (Shape s : shapes) {
            map.put(s, s.getBorder());
        }
        return map;
    }

    public void setStroke(Object snapshot) {
        Map<Shape, ColorModel> map = (Map<Shape, ColorModel>) snapshot;
        for (Map.Entry<Shape, ColorModel> entry : map.entrySet()) {
            entry.getKey().setBorder(entry.getValue());
        }
    }

    public Object getFilling() {
        Map<Shape, ColorModel> map = new HashMap<>();
        for (Shape s : shapes) {
            map.put(s, s.getFill());
        }
        return map;
    }

    @JsonIgnore
    public void setFilling(Object snapshot) {
        Map<Shape, ColorModel> map = (Map<Shape, ColorModel>) snapshot;
        for (Map.Entry<Shape, ColorModel> entry : map.entrySet()) {
            entry.getKey().setFill(entry.getValue());
        }
    }



}
