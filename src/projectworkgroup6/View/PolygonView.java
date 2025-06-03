package projectworkgroup6.View;

import javafx.scene.canvas.GraphicsContext;
import projectworkgroup6.Model.Polygon;

public class PolygonView extends ShapeView {
    public PolygonView(Polygon shape) {
        super(shape);
    }

    public void draw(GraphicsContext gc, boolean isPreview) {
        Polygon pol = (Polygon) shape;
        int n = pol.getVertices().size();
        if (n == 0) return; //se non ho ancora nessun vertice non devo disegnare nulla

        double[] xPoints = new double[n]; //vettore delle coordinate x dei vertici
        double[] yPoints = new double[n]; //vettore delle coordinate y dei vertici
        double sumX = 0, sumY = 0;
        for (int i = 0; i < n; i++) { //mi scorro tutta la lista dei vertici
            xPoints[i] = pol.getVertices().get(i)[0]; //aggiorno i due vettori perchè per disegnare mi servono gli x e gli y da passare ai metodi stroke
            yPoints[i] = pol.getVertices().get(i)[1];
            sumX += xPoints[i];
            sumY += yPoints[i];
        }

        double centerX = sumX / n;
        double centerY = sumY / n;
        double angle = pol.getRotation();

        gc.save();
        gc.translate(centerX, centerY);
        gc.rotate(angle);
        gc.translate(-centerX, -centerY);

        //Imposta i colori dinamicamente
        gc.setStroke(pol.getBorder().toColor());
        gc.setFill(pol.getFill().toColor());

        if (isPreview || n < 3) { //se è ancora una preview ovvero non ho fatto doppio click oppure ho meno di tre vertici (segmento)
            gc.strokePolyline(xPoints, yPoints, n); //Disegno mano a mano i segmenti fra i vertici
        } else {
            gc.strokePolygon(xPoints, yPoints, n); //se arruvo a chiudere la figura disegno il poligono
            gc.fillPolygon(xPoints,yPoints,n);
        }

        gc.restore();
    }

    @Override
    public void draw(GraphicsContext gc) {
        draw(gc, false); // normale
    }
}
