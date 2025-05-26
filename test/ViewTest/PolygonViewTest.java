package ViewTest;

import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import projectworkgroup6.Model.Polygon;
import projectworkgroup6.View.PolygonView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class PolygonViewTest {

    private Polygon polygon;
    private GraphicsContext gc;
    private PolygonView polygonView;

    @BeforeEach
    public void setUp() {
        polygon = new Polygon();
        gc = Mockito.mock(GraphicsContext.class); // mock del contesto grafico
    }

    @Test
    public void testDrawWithNoVertices() {
        polygon.setVertices(new ArrayList<>());
        polygonView = new PolygonView(polygon);

        polygonView.draw(gc);

        verifyNoInteractions(gc); // non dovrebbe disegnare nulla
    }

    @Test
    public void testDrawPreviewWithTwoVertices() {
        List<double[]> verts = Arrays.asList(new double[]{10.0, 20.0}, new double[]{30.0, 40.0});
        polygon.setVertices(verts);
        polygonView = new PolygonView(polygon);

        polygonView.draw(gc, true); // preview

        verify(gc, times(1)).strokePolyline(
                eq(new double[]{10.0, 30.0}),
                eq(new double[]{20.0, 40.0}),
                eq(2)
        );
    }

    @Test
    public void testDrawPolygonWithThreeVertices() {
        List<double[]> verts = Arrays.asList(
                new double[]{0.0, 0.0},
                new double[]{50.0, 0.0},
                new double[]{25.0, 50.0}
        );
        polygon.setVertices(verts);
        polygonView = new PolygonView(polygon);

        polygonView.draw(gc); // chiamata normale

        verify(gc, times(1)).strokePolygon(
                eq(new double[]{0.0, 50.0, 25.0}),
                eq(new double[]{0.0, 0.0, 50.0}),
                eq(3)
        );
    }

    @Test
    public void testDrawPolylineWhenLessThan3Vertices() {
        List<double[]> verts = Arrays.asList(
                new double[]{5.0, 5.0},
                new double[]{10.0, 10.0}
        );
        polygon.setVertices(verts);
        polygonView = new PolygonView(polygon);

        polygonView.draw(gc); // default = non preview

        verify(gc, times(1)).strokePolyline(
                eq(new double[]{5.0, 10.0}),
                eq(new double[]{5.0, 10.0}),
                eq(2)
        );
    }
}
