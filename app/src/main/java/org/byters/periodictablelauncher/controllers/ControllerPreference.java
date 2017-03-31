package org.byters.periodictablelauncher.controllers;

import android.support.annotation.NonNull;

import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.models.ModelPreference;

public class ControllerPreference {

    private static ControllerPreference instance;
    @NonNull
    private ModelPreference model;

    private ControllerPreference() {
    }

    public static ControllerPreference getInstance() {
        if (instance == null) instance = new ControllerPreference();
        return instance;
    }

    void init() {
        model = Core.getInstance().readPreferenceCache();
        if (model == null) model = new ModelPreference();
    }

    public void storeData() {
        Core.getInstance().storePreferenceCache(model);
    }

    private boolean isColorIconDefaultSetted() {
        return model.getColorAppIcon() != ModelPreference.NO_VALUE;
    }

    public int getColorIconDefault() {
        return !isColorIconDefaultSetted()
                ? Core.getInstance().getResources().getColor(R.color.text_color)
                : model.getColorAppIcon();
    }

    public void setColorIconDefault(int color) {
        model.setColorAppIcon(color);
    }

    public void setAppShadowVisibility(boolean visibility) {
        model.setShadowVisible(visibility);
    }

    public boolean isShadowVisible() {
        return model.isShadowVisible();
    }

    public int getAppListOrientation() {
        return model.getAppListOrientation();
    }

    public void setAppListOrientation(int orientation) {
        model.setAppListOrientation(orientation);
    }

    public int getSortMethod() {
        return model.getSortMethod();
    }

    public void setSortMethod(int sortMethod) {
        model.setSortMethod(sortMethod);
    }

    public int getSortOrientation() {
        return model.getSortOrientation();
    }

    public void setSortOrientation(int sortOrientation) {
        model.setSortOrientation(sortOrientation);
    }
}