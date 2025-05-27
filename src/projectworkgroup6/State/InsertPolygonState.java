package projectworkgroup6.State;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.InsertCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.Decorator.FillDecorator;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Polygon;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertPolygonState implements CanvasState {

    private final ShapeCreator creator;

    private Color currentStroke = StateController.getInstance().getStrokeColor();
    private Color currentFill = StateController.getInstance().getFillColor();

    private ColorModel border, fill;

    public InsertPolygonState(ShapeCreator creator) {
        this.creator = creator;
    }
    private Shape previewShape = null;
    private boolean isClosed = false;

    private long lastClickTime = 0;
    private static final long DOUBLE_CLICK_THRESHOLD_MS = 300;

    @Override
    public void handleClick(MouseEvent e,double x, double y, Map<Shape, ShapeView> map) {
        border = ColorModel.fromColor(currentStroke);
        fill = ColorModel.fromColor(currentFill);
        System.out.println("Click ricevuto su canvas a: (" + x + ", " + y + ")");
        if (isClosed) return;   //se non sto chiudendo il poligono, isClosed va a true se faccio doppio click

        long now = System.currentTimeMillis(); //prende l'istante di tempo in cui fa il clck
        List<double[]> vertices = creator.getTempVertices(); //mi serve una lista in cui aggiungere i vertici da collegare finchè il poligono non si vuole chiudere

        if (now - lastClickTime < DOUBLE_CLICK_THRESHOLD_MS && vertices.size() >= 3) { //se faccio doppio click e ho segnato almeno tre vertici allora posso chiudere il poligono

            System.out.println("Rilevato doppio click");
            Shape polygon = creator.createShape(x, y, border, fill); //invoco il creatore
            ShapeView view = creator.createShapeView(polygon); //aggiorno la vista
            view = new BorderDecorator(view,currentStroke);
            view = new FillDecorator(view,currentFill);
            // Inserisco Shape e View nello stato (undoable)
            InsertCommand cmd = new InsertCommand(polygon, view);
            CommandManager.getInstance().executeCommand(cmd);

            System.out.println("Poligono aggiunto alla mappa. Totale forme: " + map.size());
            creator.resetVertices(); //resetto la lista temporanea di vertici in modo tale che quando creo un nuovo poligono sarà vuota
            isClosed = true; //setto che il poligono è chiuso
            previewShape = null; //setto che non sto più facendo un preview del poligono ma è una forma finita
            lastClickTime = now;
            return;
        }else{
            lastClickTime = now;
            creator.addVertex(x, y); //se non sto chiudendo il poligono aggiungo il vertice alla lista temporanea
            previewShape = new Polygon(new ArrayList<>(creator.getTempVertices()), false, border, fill); //mi creo il poligono che se successivamente farò doppio click verrà disegnato
        }

    }

    @Override
    public void handlePression(double x, double y) {
       /* if (isClosed || creator.getTempVertices().isEmpty()) return;

        // Aggiorna l'anteprima con l'ultimo punto fluttuante
        List<double[]> temp = new ArrayList<>(creator.getTempVertices());
        temp.add(new double[]{x, y});
        previewShape = new Polygon(temp, false);*/
    }

    @Override
    public void handleMouseDragged(double x, double y) {
        // Non serve per l'inserimento poligono
    }

    @Override
    public void handleMouseReleased(double x, double y) {
        // Non serve per l'inserimento poligono
    }

    @Override
    public void recoverShapes(Map<Shape, ShapeView> map) {
        Map<Shape, ShapeView> copy = new HashMap<Shape, ShapeView>(map);
        for (Map.Entry<Shape, ShapeView> entry : copy.entrySet()) {
            Shape s = entry.getKey();
            ShapeView v = entry.getValue();

            // Deseleziona logicamente
            s.setSelected(false);

            // Se la view è decorata (cioè è un SelectedDecorator), la sostituiamo
            if (v instanceof SelectedDecorator) {
                // Rimuovi la versione decorata dalla vista (cioè dallo stato attuale)
                StateController.getInstance().removeShape(s,v);

                // Crea la versione "base" della view senza decorator
                ShapeView baseView = ((SelectedDecorator) v).undecorate();

                // Aggiungi di nuovo la versione base alla vista
                StateController.getInstance().addShape(s,baseView);

            }
        }
    }

    @Override
    public void handleDelete(KeyEvent event, Map<Shape, ShapeView> map) {

    }

    @Override
    public void handleColorChanged(Color currentStroke, Color currentFill) {
        this.currentStroke=currentStroke;
        this.currentFill=currentFill;
    }

    @Override
    public void handleKeyTyped(KeyEvent event, Map<Shape, ShapeView> map) {

    }
}
