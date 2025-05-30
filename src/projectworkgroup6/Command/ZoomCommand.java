package projectworkgroup6.Command;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * ZoomCommand è un classe che implementa Commmand per gestisce l'ingrandimento
 * o la riduzione (zoom) su un nodo target.
 * Utilizza il pattern Command per supportare operazioni di undo
 * modificando dinamicamente le proprietà di scala (`scaleX`, `scaleY`) del nodo target.
 */

public class ZoomCommand implements Command {
    private final Node target;
    private final double factor;

    //Costruisce un comando di zoom
    public ZoomCommand(Node target, double factor) {
        this.target = target;
        this.factor = factor;
    }

    //Esegue l'operazione di zoom, moltiplicando il fattore di scala del nodo
    @Override
    public void execute() {
        target.setScaleX(target.getScaleX() * factor);
        target.setScaleY(target.getScaleY() * factor);

    }

    //Annulla l'operazione di zoom riportando il nodo alla scala precedente
    @Override
    public void undo() {
        if (factor != 0) {
            target.setScaleX(target.getScaleX() / factor);
            target.setScaleY(target.getScaleY() / factor);
        }
    }
}