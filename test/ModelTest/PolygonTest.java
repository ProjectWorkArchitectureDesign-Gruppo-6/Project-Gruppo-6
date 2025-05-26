package ModelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PolygonTest {

    private Polygon polygon;
    private ArrayList<double[]> vertices;

    @BeforeEach
    void setUp() {
        vertices = new ArrayList<>();
        vertices.add(new double[]{0, 0});
        vertices.add(new double[]{4, 0});
        vertices.add(new double[]{4, 3});
        vertices.add(new double[]{0, 3});

        polygon = new Polygon(vertices, false,
                new ColorModel(0, 0, 0, 1),
                new ColorModel(255, 255, 255, 1));
    }

    @Test
    void testConstructorAndGetters() {
        List<double[]> result = polygon.getVertices();
        assertEquals(4, result.size());
        assertArrayEquals(new double[]{0, 0}, result.get(0), 0.001);
    }

    @Test
    void testSetVerticesCreatesDeepCopy() {
        List<double[]> newVertices = new ArrayList<>();
        newVertices.add(new double[]{1, 1});
        newVertices.add(new double[]{2, 1});
        polygon.setVertices(newVertices);

        newVertices.get(0)[0] = 999;
        assertNotEquals(999, polygon.getVertices().get(0)[0]);
    }

    @Test
    void testGetXcYc() {
        assertEquals(0.0, polygon.getXc(), 0.001);
        assertEquals(0.0, polygon.getYc(), 0.001);
    }

    @Test
    void testGetDim1Dim2() {
        assertEquals(4.0, polygon.getDim1(), 0.001);
        assertEquals(3.0, polygon.getDim2(), 0.001);
    }

    @Test
    void testMove() {
        polygon.move(1, 2);
        List<double[]> moved = polygon.getVertices();

        assertArrayEquals(new double[]{1, 2}, moved.get(0), 0.001);
        assertArrayEquals(new double[]{5, 2}, moved.get(1), 0.001);
    }

    @Test
    void testResize() {
        polygon.resize(2);
        List<double[]> resized = polygon.getVertices();

        assertArrayEquals(new double[]{0, 0}, resized.get(0), 0.001);  // center stays same
        assertArrayEquals(new double[]{8, 0}, resized.get(1), 0.001);  // doubled in width
        assertArrayEquals(new double[]{8, 6}, resized.get(2), 0.001);
    }

    @Test
    void testContainsInside() {
        assertTrue(polygon.contains(2, 1));
    }

    @Test
    void testContainsOutside() {
        assertFalse(polygon.contains(10, 10));
    }

    @Test
    void testContainsOnEdge() {
        assertTrue(polygon.contains(0, 1.5));  // on left edge
    }
}
