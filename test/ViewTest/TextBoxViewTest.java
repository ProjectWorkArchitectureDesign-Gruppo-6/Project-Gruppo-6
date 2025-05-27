package ViewTest;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.TextBox;
import projectworkgroup6.View.TextBoxView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TextBoxViewTest {

    private GraphicsContext gc;
    private TextBox textBox;
    private TextBoxView textBoxView;

    @BeforeEach
    void setUp() {
        gc = mock(GraphicsContext.class);

        // Crea un TextBox con testo multilinea per test
        textBox = new TextBox(0, 0, false,
                200, 100,
                new ColorModel(0, 0, 0, 1.0),
                new ColorModel(1, 1, 1, 1.0),
                "Hello\nWorld", "Arial", 12, new ColorModel(0, 0, 0, 1.0));

        textBox.setEditing(true);

        textBoxView = new TextBoxView(textBox);
    }

    @Test
    void testDrawSetsFontAndFillColor() {
        textBoxView.draw(gc);

        // Verifica che venga impostato il font (non possiamo verificare il font esatto, ma che venga chiamato)
        verify(gc).setFont(any(Font.class));

        // Verifica che venga impostato il fill con il colore corretto (nero)
        verify(gc).setFill(any(Color.class));
    }

    @Test
    void testDrawCallsFillTextMultipleTimesForMultiline() {
        textBoxView.draw(gc);

        // Dovrebbe chiamare fillText almeno due volte (una per "Hello" e una per "World")
        verify(gc, atLeast(2)).fillText(anyString(), anyDouble(), anyDouble());
    }

    @Test
    void testDrawDrawsStrokeIfEditing() {
        textBox.setEditing(true);
        textBoxView.draw(gc);

        verify(gc).setStroke(Color.BLACK);
        verify(gc).setLineWidth(1.5);
        verify(gc).strokeRect(textBox.getXc(), textBox.getYc(), textBox.getWidth(), textBox.getHeight());
    }

    @Test
    void testDrawDoesNotDrawStrokeIfNotEditing() {
        textBox.setEditing(false);
        textBoxView.draw(gc);

        verify(gc, never()).strokeRect(anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }
}
