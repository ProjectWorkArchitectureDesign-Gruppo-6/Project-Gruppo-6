package projectworkgroup6.Command;

import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;


/**
 * Comando che rappresenta un'operazione di rotazione su una shape.
 * Viene utilizzato per supportare l’operazione di undo nel pattern Command.
 * La rotazione vera e propria viene eseguita dinamicamente durante il drag,
 * ma questo comando registra l'angolo iniziale e finale per consentire il ripristino.
 */

public class RotateCommand implements Command {
    private final Shape shape;
    private double oldAngle;
    private double newAngle;

    public RotateCommand(Shape shape) {
        this.shape = shape;
        this.oldAngle = shape.getRotation();
    }

    //Metodo che deve essere chiamato alla fine del drag per salvare l'angolo finale della rotazione.
    //Serve per tracciare correttamente lo stato da ripristinare durante un undo.
    public void finalizeRotation() {
        this.newAngle = shape.getRotation();
    }

    //Non esegue alcuna azione diretta, perchè la rotazione avviene durante il drag.
    //Questo metodo è richiesto perchè la classe RotateCommand implementa Command, ma non applica modifiche.
    @Override
    public void execute() {

    }

    //Ripristina l'angolo iniziale salvato prima della rotazione.
    //Serve per annullare l'operazione di rotazione.
    @Override
    public void undo() {
        shape.setRotation(oldAngle);
        StateController.getInstance().notifyCanvasToRepaint();
    }
}
