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
        /*per la rotazione*/
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;
        double angle = tb.getRotation();
        /*********/
        double padding = 10;

        Font font = new Font(tb.getFontFamily(), tb.getFontSize());
        gc.setFont(font);
        gc.setFill(tb.getFontColor().toColor());

        double maxWidth = width - padding;
        double maxHeight = height + 2 * padding;
        double lineHeight = font.getSize();

        String text = tb.getText();
        StringBuilder line = new StringBuilder();
        int count = 1;


        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            line.append(c);
            double lineWidth = computeStringWidth(line.toString(), font);

            if (lineWidth > maxWidth || c == '\r') {
                count++; //aggiorno il numero di linee ogni volta che vado a capo
                if (c != '\n' && c != '\r') { //prova && dopo
                    line = new StringBuilder().append(c);
                } else {
                    line = new StringBuilder();
                }
            }
        }


        //il primo for è per calcolarmi questa altezza prima di mostrare il testo a video altrimenti si vedrebbe prima il testo uscire dalla casella e poi il ridimensionamento
        double requiredHeight = count * 1.3*lineHeight + 2* padding; //calcolo l'altezza necessaria per l'inserimento del carattere lasciando un po' di margine

        if (requiredHeight > tb.getHeight()) {
            tb.setHeight(requiredHeight);
            height = requiredHeight;
        }

        // Applico trasformazioni
        gc.save();
        gc.translate(centerX, centerY);
        gc.rotate(angle);
        gc.translate(-centerX, -centerY);

        line.setLength(0);
        double currentY = y + padding; //currentY corrisponde alla posizione y in cui deve comparire il testo

        for (int i = 0; i < text.length(); i++) {
            //in questo for creo la linea con le informazioni corrette
            char c = text.charAt(i);
            line.append(c);
            double lineWidth = computeStringWidth(line.toString(), font);

            if (lineWidth > maxWidth || c == '\n' || c=='\r') {
                if (c != '\n' && c != '\r') {
                    line.setLength(line.length() - 1);
                    gc.fillText(line.toString(), x + padding, currentY);
                    line = new StringBuilder().append(c);
                } else {
                    line.setLength(line.length() - 1);
                    gc.fillText(line.toString(), x + padding, currentY);
                    line = new StringBuilder();
                }
                currentY += lineHeight;
            }
        }


        if (!line.toString().isEmpty()) {
            gc.fillText(line.toString(), x + padding, currentY);
        }


        if (tb.isEditing()) {
            //se sono nella fase di editing mostro il rettangolo attorno
            gc.setStroke(Color.LIGHTGREY);
            gc.setLineWidth(1.5);
            gc.setLineDashes(5);
            gc.strokeRect(x, y, width, Math.max(maxHeight, requiredHeight));
            gc.setLineDashes(0);
        }

        gc.restore();
        /*if (requiredHeight > tb.getHeight()) {
            //aggiorno l'altezza del rettangolo
            tb.setHeight(requiredHeight);
        }*/
    }

    private double computeStringWidth(String text, Font font) {
        double averageCharWidth = font.getSize() * 0.6;
        return text.length() * averageCharWidth + 2; // piccolo margine di tolleranza
    }

}