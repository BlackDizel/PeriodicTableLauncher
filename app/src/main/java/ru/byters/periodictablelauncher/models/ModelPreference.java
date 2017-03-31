package ru.byters.periodictablelauncher.models;

import java.io.Serializable;

public class ModelPreference implements Serializable {
    public static final int NO_VALUE = -1;
    private int colorAppIcon;
    private boolean shadowVisible;

    public ModelPreference() {
        colorAppIcon = NO_VALUE;
        shadowVisible = true;
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
}
