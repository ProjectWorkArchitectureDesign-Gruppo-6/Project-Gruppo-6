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

        String text = tb.getText();
        StringBuilder line = new StringBuilder();
        int count = 1;
        double lineHeight = font.getSize();
        double maxWidth = width - 2 * padding;

        // Conto le righe necessarie
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            line.append(c);
            double lineWidth = computeStringWidth(line.toString(), font);

            if (lineWidth > maxWidth || c == '\r' || c == '\n') {
                count++; //aggiorno il numero di linee ogni volta che vado a capo
                line = new StringBuilder();
            }
        }

        //calcolo l'altezza necessaria per l'inserimento del carattere lasciando un po' di margine
       /* double requiredHeight = count * 1.3 * lineHeight + 2 * padding;
        if (requiredHeight > tb.getHeight()) {
            tb.setHeight(requiredHeight);
            height = requiredHeight;
        }else {*/
            height = tb.getHeight(); // usa l’altezza corrente, non la sovrascrive


        // Rotazione e disegno della text box
        gc.save();
        gc.translate(centerX, centerY);
        gc.rotate(angle);
        gc.translate(-centerX, -centerY);

        double currentY = y + padding; //currentY corrisponde alla posizione y in cui deve comparire il testo
        line.setLength(0);
        gc.setFill(tb.getFontColor().toColor());

        for (int i = 0; i < text.length(); i++) {
            //in questo for creo la linea con le informazioni corrette
            char c = text.charAt(i);
            line.append(c);
            double lineWidth = computeStringWidth(line.toString(), font);

            if (lineWidth > maxWidth || c == '\n' || c == '\r') {
                line.setLength(line.length() - 1);
                gc.fillText(line.toString(), x + padding, currentY);
                currentY += 1.3 * lineHeight;
                line = new StringBuilder();
            }
        }

        if (!line.toString().isEmpty()) {
            gc.fillText(line.toString(), x + padding, currentY);
        }

        // Disegno il decorator solo se sono in editing
        if (tb.isEditing()) {
            gc.setStroke(Color.LIGHTGREY);
            gc.setLineWidth(1.5);
            gc.setLineDashes(5);
            gc.strokeRect(x, y, width, height);
            gc.setLineDashes(0);
        }

        gc.restore();
    }

    private double computeStringWidth(String text, Font font) {
        double averageCharWidth = font.getSize() * 0.6;
        return text.length() * averageCharWidth + 2;
    }

}