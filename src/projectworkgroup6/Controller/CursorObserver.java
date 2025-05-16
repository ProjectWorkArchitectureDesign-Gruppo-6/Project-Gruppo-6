package projectworkgroup6.Controller;

import projectworkgroup6.Factory.ShapeCreator;

import projectworkgroup6.Model.CursorMode;

public interface CursorObserver {
    void onCursorModeChanged(CursorMode newMode);

    void onCurrentCreatorChanged(ShapeCreator currentCreator);

}
