package projectworkgroup6.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ellipse extends Shape {


    private double diam1;
    private double diam2;
    public Ellipse() {
        super(0, 0, false, 0,0,new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 0,0);
    }

    public Ellipse(double x, double y, boolean selected, double diam1, double diam2, ColorModel border, ColorModel fill, int layer, int group) {
        super(x,y,selected, diam1, diam2, border, fill, layer, group);

    }

    public double getDiam1() {
        return diam1;
    }


    public double getDiam2() {
        return diam2;
    }




    @Override
    public double getDim1() {
        return width;
    }

    @Override
    public double getDim2() {
        return height;
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
        return this.getX() - width/2;
    }  // Xc e Yc angolo in alto a sinistra

    @Override
    public double getYc() {
        return this.getY() - height/2;
    }



    @Override
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void resize(double factorX, double factorY, double dx, double dy) {
        width *= factorX;
        height *= factorY;
    }

    /*width e height sono diam1 e diam2*/
    @Override
    public void stretch(double dx, double dy, String id) {

        this.x += dx / 2;
        this.y += dy / 2;
        if (id.equals("RIGHT") || id.equals("UP")) {
            this.width = Math.max(width + dx + dy , 1);
            this.height = Math.max(height - dx -dy, 1);
        }
        if (id.equals("LEFT") || id.equals("DOWN")) {
            this.width = Math.max(width -dx -dy,1);
            this.height =  Math.max(height +dx +dy,1);
        }

    }
        /*@Override
        public void stretch(double dx, double dy, String id) {

            this.x += dx / 2;
            this.y += dy / 2;
            if (id.equals("RIGHT") || id.equals("UP")) {
                this.diam1 = Math.max(diam2 + dx + dy , 1);
                this.diam2 = Math.max(diam2 - dx -dy, 1);
            }
            if (id.equals("LEFT") || id.equals("DOWN")) {
                this.diam1 = Math.max(diam1 -dx -dy,1);
                this.diam2 =  Math.max(diam2 +dx +dy,1);
            }


        }*/

    @Override
    public boolean contains(double x, double y) {
        double left = this.getX() - width / 2;
        double top = this.getY() - height / 2;
        return x >= left && x <= left + width && y >= top && y <= top + height;
    }

    @Override
    public String type() {
        return "Ellipse";
    }

    @Override
    public Shape cloneAt(double x, double y, int layer){
        return new Ellipse(x, y, false, this.width, this.height, this.border, this.fill, layer, this.group);
    }
}
