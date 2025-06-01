package projectworkgroup6.Controller;

import projectworkgroup6.Model.Shape;

public interface GroupObserver {
    void onShapeSelected(Shape s);
    void onShapeDeselected();
    void onMouseRightClick(double x, double y);
}
