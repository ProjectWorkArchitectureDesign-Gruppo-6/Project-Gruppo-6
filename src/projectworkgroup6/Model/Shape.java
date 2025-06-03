package projectworkgroup6.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javafx.scene.paint.Color;
import projectworkgroup6.Model.ColorModel;

import java.util.HashMap;
import java.util.Map;

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
        @JsonSubTypes.Type(value = TextBox.class, name = "TextBox"),
        @JsonSubTypes.Type(value = Group.class, name = "Group")
})
public abstract class Shape {

    protected double x, y;
    private double rotation = 0.0;
    protected ColorModel border, fill;
    protected int layer;
    protected int group;
    protected double width, height;

    public Shape(double x, double y, boolean selected, double width, double height, ColorModel border, ColorModel fill, int layer, int group) {
        this.x = x;
        this.y = y;
        this.selected = selected;
        this.width = width;
        this.height = height;
        this.border = border;
        this.fill = fill;
        this.layer = layer;
        this.group = group;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public ColorModel getBorder() {
        return border;
    }

    public void setBorder(ColorModel border) {
        this.border = border;
    }


    public Object getStroke() {
        return this.border;
    }

    @JsonIgnore
    public void setStroke(Object snapshot) {
        this.setBorder((ColorModel) snapshot);
    }

    public Object getFilling() {
        return this.fill;
    }

    @JsonIgnore
    public void setFilling(Object snapshot) {
        this.setFill((ColorModel) snapshot);
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

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public abstract double getDim1();
    public abstract double getDim2();
    public abstract void setDim1(double dim1);
    public abstract void setDim2(double dim2);



    public abstract double getXc();
    public abstract double getYc();

    public double getX2() {
        return this.getXc() + this.getDim1();
    }

    public double getY2() {
        return this.getYc() + this.getDim2();
    }

    protected boolean selected = false;

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }


    public abstract void move(double dx, double dy);

    public abstract void resize(double factorX, double factorY, double dx, double dy);

    public abstract void stretch(double dx, double dy, String id);


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

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public void layerUp(){
        this.layer ++;
    }

    public void layerDown(){
        this.layer --;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public abstract Shape cloneAt(double x, double y, int layer);
}
