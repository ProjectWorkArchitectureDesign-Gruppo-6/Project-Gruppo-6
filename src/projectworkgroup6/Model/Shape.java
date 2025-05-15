package projectworkgroup6.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Shape {
    void draw(GraphicsContext gc);
    void move(double dx, double dy);
    void resize(double scaleFactor);
    void setColor(Color color);
}
