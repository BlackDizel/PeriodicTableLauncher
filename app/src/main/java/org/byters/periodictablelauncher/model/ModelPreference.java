package org.byters.periodictablelauncher.model;

import java.io.Serializable;

public class ModelPreference implements Serializable {
    public static final int NO_VALUE = -1;
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    public static final int SORT_FULLTITLE = 0;
    public static final int SORT_LABEL = 1;
    public static final int SORT_DATE = 2;
    public static final int SORT_ORIENT_ASC = 0;
    public static final int SORT_ORIENT_DESC = 1;
    private int colorAppIcon;
    private boolean shadowVisible;
    private int appListOrientation;
    private int sortMethod;
    private int sortOrientation;

    public ModelPreference() {
        colorAppIcon = NO_VALUE;
        shadowVisible = true;
        appListOrientation = ORIENTATION_HORIZONTAL;
        sortMethod = SORT_FULLTITLE;
        sortOrientation = SORT_ORIENT_ASC;
    }

    public int getSortMethod() {
        return sortMethod;
    }

    public void setSortMethod(int sortMethod) {
        this.sortMethod = sortMethod;
    }

    public int getSortOrientation() {
        return sortOrientation;
    }

    public void setSortOrientation(int sortOrientation) {
        this.sortOrientation = sortOrientation;
    }

    public int getColorAppIcon() {
        return colorAppIcon;
    }

    public void setColorAppIcon(int colorAppIcon) {
        this.colorAppIcon = colorAppIcon;
    }

    public boolean isShadowVisible() {
        return shadowVisible;
    }

    public void setShadowVisible(boolean shadowVisible) {
        this.shadowVisible = shadowVisible;
    }

    public int getAppListOrientation() {
        return appListOrientation;
    }

    public void setAppListOrientation(int orientation) {
        this.appListOrientation = orientation;
    }
}
