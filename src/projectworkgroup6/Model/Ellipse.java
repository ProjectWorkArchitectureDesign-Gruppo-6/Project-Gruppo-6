package projectworkgroup6.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ellipse extends Shape {
    private double diam1, diam2;


    public Ellipse() {
        super(0, 0, false, new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 0,0);
    }

    public Ellipse(double x, double y, boolean selected, double diam1, double diam2, ColorModel border, ColorModel fill, int layer, int group) {
        super(x,y,selected, border, fill, layer, group);
        this.diam1 = diam1;
        this.diam2 = diam2;
    }

    public double getDiam1() {
        return diam1;
    }


    public double getDiam2() {
        return diam2;
    }



    @Override
    public double getDim1() {
        return diam1;
    }

    @Override
    public double getDim2() {
        return diam2;
    }
    @Override
    public void setDim1(double diam1) {
        this.diam1 = diam1;
    }
    @Override
    public void setDim2(double diam2) {
        this.diam2 = diam2;
    }

    @Override
    public double getXc() {
        return this.getX() - diam1/2;
    }  // Xc e Yc angolo in alto a sinistra

    @Override
    public double getYc() {
        return this.getY() - diam2/2;
    }



    @Override
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void resize(double factor, double dx, double dy) {
        diam1 *= factor;
        diam2 *= factor;
    }

        @Override
        public void stretch(double dx, double dy, String id) {

            this.x += dx / 2;
            this.y += dy / 2;
            if (id.equals("RIGHT") || id.equals("UP")) {
                this.diam1 = Math.max(diam1 + dx + dy , 1);
                this.diam2 = Math.max(diam2 - dx -dy, 1);
            }
            if (id.equals("LEFT") || id.equals("DOWN")) {
                this.diam1 = Math.max(diam1 -dx -dy,1);
                this.diam2 =  Math.max(diam2 +dx +dy,1);
            }


        }

    @Override
    public boolean contains(double x, double y) {
        double left = this.getX() - diam1 / 2;
        double top = this.getY() - diam2 / 2;
        return x >= left && x <= left + diam1 && y >= top && y <= top + diam2;
    }

    @Override
    public String type() {
        return "Ellipse";
    }

    @Override
    public Shape cloneAt(double x, double y, int layer){
        return new Ellipse(x, y, false, this.diam1, this.diam2, this.border, this.fill, layer, this.group);
    }
}
