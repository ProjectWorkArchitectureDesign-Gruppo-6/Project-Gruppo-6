import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Command.ResizeCommand;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;

import static org.junit.jupiter.api.Assertions.*;

public class ResizeCommandTest {

    private TestShape shape;
    private ResizeCommand resizeCommand;

    @BeforeEach
    void setUp() {
        shape = new TestShape(100, 100, 50, 30);
        resizeCommand = new ResizeCommand(shape);
    }

    @Test
    void testExecute() {
        resizeCommand.accumulate(2.0, 2.0);
        resizeCommand.execute();

        assertEquals(100, shape.getX());
        assertEquals(100, shape.getY());
        assertEquals(100.0, shape.getDim1());
        assertEquals(60.0, shape.getDim2());
    }


    @Test
    void testMultipleAccumulates() {
        resizeCommand.accumulate(2.0, 2.0);
        resizeCommand.accumulate(0.5, 0.25);
        resizeCommand.execute();

        assertEquals(50.0, shape.getDim1()); // 50 * 1.0
        assertEquals(15, shape.getDim2()); // 30 * 0.5
    }

    // Mock Shape per test
    static class TestShape extends Shape {
        public TestShape(double x, double y, double width, double height) {
            super(x, y, false, width, height, new ColorModel(0,0,0,1), new ColorModel(1,1,1,1), 1, 0);
        }

        @Override public double getDim1() { return width; }
        @Override public double getDim2() { return height; }
        @Override public void setDim1(double dim1) { this.width = dim1; }
        @Override public void setDim2(double dim2) { this.height = dim2; }
        @Override public void move(double dx, double dy) { this.x += dx; this.y += dy; }
        @Override public void resize(double factorX, double factorY, double cx, double cy) {
            this.width *= factorX;
            this.height *= factorY;
        }
        @Override public void stretch(double dx, double dy, String id) {}
        @Override public boolean contains(double x, double y) { return false; }
        @Override public String type() { return "TestShape"; }
        @Override public double getXc() { return x; }
        @Override public double getYc() { return y; }
        @Override public Shape cloneAt(double x, double y, int layer) {
            return new TestShape(x, y, this.width, this.height);
        }
    }
}
