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
        super(0, 0, false, new ColorModel(0,0,0,1), new ColorModel(255,255,255,1));
    }

    public Polygon(ArrayList<double []> vertices, boolean selected, ColorModel border, ColorModel fill) {
        super(vertices.get(0)[0], vertices.get(0)[1], selected, border, fill); //get(0) passo gli indici del primo click
         //per la creazione del poligono io mi salvo quei vertici in una list che non
                                           // può essere modificata dall'esterno (new interno al costruttore) altrimenti varierebbe il poligono
        this.vertices = new ArrayList<>(); //perhcè altrimenti stai modificando una lista durante l'iterazione quindi ne crei una temporanea
        for (double[] v : vertices) {
            this.vertices.add(new double[] { v[0], v[1] });
        }
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
        /*stream itera su tutti i vertici della lista, maptoDouble serve a prendere solo la coordinata x di ogni punto
         * e ne calcola la media, per iterare tramite stream serve verificare che ci siano oggetti nella lista
         * se la lista è vuota restituisce x del costruttore */
        return vertices.stream().mapToDouble(v->v[0]).min().orElse(x);
    }

    @Override
    public double getYc() {
        return vertices.stream().mapToDouble(v->v[1]).min().orElse(y);
    }


    //getDim1 e getDim2 restituiscono lunghezza e altezza del rettangolo che racchiude tutti i vertici, non restituiscono le coordinate di punti
    @Override
    public double getDim1() {
        double Xmin = vertices.stream().mapToDouble(v->v[0]).min().orElse(x); //si prende il vertice con la coordinata x minima
        double Xmax = vertices.stream().mapToDouble(v->v[0]).max().orElse(x);
        return Xmax - Xmin;
    }

    @Override
    public double getDim2() {
        double Ymin = vertices.stream().mapToDouble(v->v[1]).min().orElse(y);
        double Ymax = vertices.stream().mapToDouble(v->v[1]).max().orElse(y);
        return Ymax - Ymin;
    }

    @Override
    public void move(double dx, double dy) {
        for (double [] v : vertices){
            v[0]+=dx;
            v[1]+=dy;
        }
    }

    @Override
    public void resize(double factor) {
        double centerX = getXc(); // calcola centro X
        double centerY = getYc(); // calcola centro Y

        for (double[] v : vertices) {
            v[0] = centerX + (v[0] - centerX) * factor; // nuova x
            v[1] = centerY + (v[1] - centerY) * factor; // nuova y
        }
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
    public Shape cloneAt(double x, double y){
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

        return new Polygon((ArrayList<double[]>) newVertices, false, border, fill);
    }

}
