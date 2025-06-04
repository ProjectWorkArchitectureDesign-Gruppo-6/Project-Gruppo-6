package DecoratorTest;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Decorator.FillDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class FillDecoratorTest {

    private ShapeView baseView;
    private Shape mockedShape;
    private FillDecorator decorator;

    @BeforeEach
    void setUp() {
        mockedShape = mock(Shape.class); // Shape del model
        baseView = mock(ShapeView.class);
        when(baseView.getShape()).thenReturn(mockedShape);

        decorator = new FillDecorator(baseView, Color.BLUE);
    }

    @Test
    void testUndecorateReturnsBaseView() {
        ShapeView undecorated = decorator.undecorate();
        assertSame(baseView, undecorated, "undecorate() should return the base view");
    }

}
