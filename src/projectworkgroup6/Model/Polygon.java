package projectworkgroup6.Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Polygon extends Shape{

    @JsonProperty("vertices")
    private ArrayList<double[]> vertices;

    public Polygon() {
        super(0, 0, false, 0,0, new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 0,0);
    }

    public Polygon(ArrayList<double []> vertices, boolean selected, double width, double height, ColorModel border, ColorModel fill, int layer, int group) {
        super(vertices.get(0)[0], vertices.get(0)[1], selected, width, height,border, fill, layer, group); //get(0) passo gli indici del primo click
        this.vertices = new ArrayList<>(); //perchè altrimenti stai modificando una lista durante l'iterazione quindi ne crei una temporanea
        for (double[] v : vertices) {
            this.vertices.add(new double[] { v[0], v[1] });
        }

        correctCenter();

    }

    private void correctCenter() {
        setX(this.getXc() + this.getDim1()/2);
        setY(this.getYc() + this.getDim2()/2);
    }

    @JsonProperty("vertices")
    public List<double[]> getVertices() {
        return vertices;
    }

    @JsonProperty("vertices")
    public void setVertices(List<double[]> vertices) {
        this.vertices = new ArrayList<>();
        for (double[] v : vertices) {
            this.vertices.add(new double[]{v[0], v[1]});
        }
    }

    //tramite getXc() e getYc() ottieni la coordinate del punto più in alto a sinistra del poligono

    @Override
    public double getXc() {
        // restituisce x del vertice in alto a sinistra
        return vertices.stream().mapToDouble(v->v[0]).min().orElse(x);
    }

    @Override
    public double getYc() {
        // restituisce y del vertice in alto a sinistra
        return vertices.stream().mapToDouble(v->v[1]).min().orElse(y);
    }


    //getDim1 e getDim2 restituiscono lunghezza e altezza del rettangolo che racchiude tutti i vertici, non restituiscono le coordinate di punti
    @Override
    public double getDim1() { // calcolo di width ed height nel creatore, ottenuto dai vertici.
        return width;
    }

    @Override
    public double getDim2() {
        return height;
    }

    @Override
    public void move(double dx, double dy) {
        for (double [] v : vertices){
            v[0]+=dx;
            v[1]+=dy;
        }
    }


    @Override
    public void resize(double factorX, double factorY, double dx, double dy) {
        double centerX = getX(); // calcola centro X
        double centerY = getY(); // calcola centro Y

        for (double[] v : vertices) {
            v[0] = centerX + (v[0] - centerX) * factorX; // nuova x
            v[1] = centerY + (v[1] - centerY) * factorY; // nuova y
        }

        double Xmin = vertices.stream().mapToDouble(v->v[0]).min().orElse(x); //si prende il vertice con la coordinata x minima
        double Xmax = vertices.stream().mapToDouble(v->v[0]).max().orElse(x);
        width =  Xmax - Xmin;

        double Ymin = vertices.stream().mapToDouble(v->v[1]).min().orElse(y);
        double Ymax = vertices.stream().mapToDouble(v->v[1]).max().orElse(y);
        height = Ymax - Ymin;


    }

    @Override
    public boolean contains(double x, double y) {
        int intersections = 0;
        int n = vertices.size();

        for (int i = 0; i < n; i++) {
            double[] v1 = vertices.get(i);
            double[] v2 = vertices.get((i + 1) % n); // vertice successivo (chiusura con modulo)

            double x1 = v1[0], y1 = v1[1];
            double x2 = v2[0], y2 = v2[1];

            // Verifica se il segmento (v1,v2) interseca il raggio orizzontale a destra di (x,y)
            if (((y1 > y) != (y2 > y)) &&
                    (x < (x2 - x1) * (y - y1) / (y2 - y1) + x1)) {
                intersections++;
            }
        }

        return (intersections % 2) == 1; // inside se numero intersezioni dispari
    }

    @Override
    public String type() {
        return "Polygon";
    }

    @Override
    public Shape cloneAt(double x, double y, int layer){
        List<double[]> newVertices = new ArrayList<>();
        double sumX = 0, sumY = 0;
        for (double[] v : this.vertices) {
            sumX += v[0];
            sumY += v[1];
        }
        double centerX = sumX / vertices.size();
        double centerY = sumY / vertices.size();
        double dx = x - centerX;
        double dy = y - centerY;

        for (double[] v : this.vertices) {
            newVertices.add(new double[]{v[0] + dx, v[1] + dy});
        }

        return new Polygon((ArrayList<double[]>) newVertices, false, this.getDim1(), this.getDim2(), border, fill,layer,this.group);
    }

    @Override
    public void setX(double x) {
        double oldc = this.getX();
        this.x = x;
        double dx = x-oldc;
        for (double[] v : vertices) {
            v[0] = v[0] + dx; // nuova x
        }
    }

    @Override
    public void setY(double y) {
        double oldc = this.getY();
        this.y = y;
        double dy = y-oldc;
        for (double[] v : vertices) {
            v[1] = v[1] + dy; // nuova x
        }

    }




}
