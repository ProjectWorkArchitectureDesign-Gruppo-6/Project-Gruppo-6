package DecoratorTest;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Decorator.FillDecorator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import static org.mockito.Mockito.*;

public class FillDecoratorTest {

    private ShapeView mockBaseView;
    private FillDecorator fillDecorator;
    private Color fillColor = Color.RED;

    @BeforeEach
    public void setUp() {
        mockBaseView = mock(ShapeView.class);
        Shape shape = new Rectangle(0,0,false,20,10,new ColorModel(0,0,0,1),new ColorModel(255,255,255,1)); // forma fittizia
        when(mockBaseView.getShape()).thenReturn(shape);
        fillDecorator = new FillDecorator(mockBaseView, fillColor);
    }



    @Test
    public void testUndecorateReturnsBaseView() {
        assert fillDecorator.undecorate() == mockBaseView;
    }
}
