package projectworkgroup6.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rectangle extends Shape {
    private double width, height;


    // Per serializzazione
    public Rectangle() {
        super(0, 0, false, new ColorModel(0,0,0,1), new ColorModel(255,255,255,1), 0,0);
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
    public void setDim1(double dim1) {
        this.width = dim1;
    }

    @Override
    public void setDim2(double dim2) {
this.height=dim2;
    }

    public double getXc(){
        return this.getX() - width/2;
    } // Xc è la coordinata del lato sinistro
    public double getYc(){
        return this.getY() - height/2;
    } // Yc è la coordinata del lato superiore



    public Rectangle(double x, double y, boolean selected, double width, double height, ColorModel border, ColorModel fill, int layer, int group) {
        super(x, y, selected, border, fill, layer, group);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }


    public double getHeight() {
        return height;
    }


    public void setHeight(double height) {
        this.height = height;
    }



    @Override
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }


    @Override
    public void resize(double factor, double dx, double dy) {
        width *= factor;
        height *= factor;

    }

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



    @Override
    public boolean contains(double x, double y) {
        double left = this.getX() - width/2;
        double top = this.getY() - height/2;
        return x >= left && x <= left + width && y >= top && y <= top + height;
    }

    @Override
    public String type() {
        return "Rectangle";
    }

    @Override
    public Shape cloneAt(double x, double y, int layer){
        return new Rectangle(x, y, false, this.width, this.height, this.border, this.fill, layer, this.group);
    }

}