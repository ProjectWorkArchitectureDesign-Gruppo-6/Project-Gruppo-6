package projectworkgroup6.View;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import projectworkgroup6.Model.TextBox;

public class TextBoxView extends ShapeView {

    public TextBoxView(TextBox shape) {
        super(shape);
    }

    @Override
    public void draw(GraphicsContext gc) {
        TextBox tb = (TextBox) shape;

        double x = tb.getXc();
        double y = tb.getYc();
        double width = tb.getWidth();
        double height = tb.getHeight();
        double padding = 10; //serve a non far scrivere troppo vicino al margine

        // Imposto il font come tipo, dimensione e colore
        Font font = new Font(tb.getFontFamily(), tb.getFontSize());
        gc.setFont(font);
        gc.setFill(tb.getFontColor().toColor());


        double maxWidth = width - 2 * padding; //serve a calcolare la lunghezza del testo rispetto alla casella per gestire l'andata a capo automatica
        double maxHeight = height - 2 * padding;
        double lineHeight = font.getSize(); //dimensione di ogni riga in base alla dimensione del font per andare a capo

        String text = tb.getText(); //prendo i caratteri tramite getText
        StringBuilder line = new StringBuilder(); //uso StringBuilder per costruire la stringa ci metto tutti i caratteri di una linea
        double currentY = y + padding; //serve per gestire la fine della casella di testo in altezza


        for (int i = 0; i < text.length(); i++) { //itero sulla lunghezza della riga
            char c = text.charAt(i);

            line.append(c); //accumulo i caratteri

            // Calcolo della larghezza della riga
            double lineWidth = computeStringWidth(line.toString(), font); //calcolo la larghezza attuale
            if (lineWidth > maxWidth || c == '\n') { //se supero la larghezza del box o va l'utente a capo

                if (c != '\n') { //se non è andato l'utente
                    line.setLength(line.length() - 1); // rimuovi ultimo carattere
                    gc.fillText(line.toString(), x + padding, currentY); //salvo la riga corrente
                    line = new StringBuilder().append(c); // creo una nuova linea partendo dal carattere eliminato
                } else {
                    line.setLength(line.length() - 1); // se l'utente va a capo non stampo \n ma vado a capo
                    gc.fillText(line.toString(), x + padding, currentY);
                    line = new StringBuilder(); // creo una nuova linea
                }
                currentY += lineHeight; //aggiorno l'altezza occupata

            }

        }



        // Stampa l’ultima riga rimasta
        if (!line.toString().isEmpty() && currentY <= y + height - padding) {
            gc.fillText(line.toString(), x + padding, currentY); //se c'era ancora un'ultima riga senza superare i margini la stampa
        }

        if(tb.isEditing()){ //se sono in modalità editing mostro il bordo
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1.5);
            gc.strokeRect(x, y, width, height);
        }
    }

    //serve a calcolare la larghezza di una stringa in base al font
    private double computeStringWidth(String text, Font font) {
        double averageCharWidth = font.getSize() * 0.6;
        return text.length() * averageCharWidth + 2; // piccolo margine di tolleranza
    }
}