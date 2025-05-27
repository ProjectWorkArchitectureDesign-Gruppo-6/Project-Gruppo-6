package projectworkgroup6.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import projectworkgroup6.Model.ColorModel;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Rectangle.class, name = "Rectangle"),
        @JsonSubTypes.Type(value = Ellipse.class, name = "Ellipse"),
        @JsonSubTypes.Type(value = Line.class, name = "Line"),
        @JsonSubTypes.Type(value = Polygon.class, name = "Polygon"),
        @JsonSubTypes.Type(value = TextBox.class, name = "TextBox")
})
public abstract class Shape {

    protected double x, y;
    protected ColorModel border, fill;

    public Shape(double x, double y, boolean selected, ColorModel border, ColorModel fill) {
        this.x = x;
        this.y = y;
        this.selected = selected;
        this.border = border;
        this.fill = fill;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public ColorModel getBorder() {
        return border;
    }

    public void setBorder(ColorModel border) {
        this.border = border;
    }

    public ColorModel getFill() {
        return fill;
    }

    public void setFill(ColorModel fill) {
        this.fill = fill;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public abstract double getDim1();
    public abstract double getDim2();


    public abstract double getXc();
    public abstract double getYc();
    protected boolean selected = false;

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }


    public abstract void move(double dx, double dy);
    public abstract void resize(double scaleFactor);


    public abstract boolean contains(double x, double y);

    public abstract String type();

    @JsonIgnore
    public Shape getShapebase() {
        return this;
    }

    public void setEditing(boolean b) {
    }

    public String getText() {
        return null;
    }

    public void setText(String newText) {
    }
}
