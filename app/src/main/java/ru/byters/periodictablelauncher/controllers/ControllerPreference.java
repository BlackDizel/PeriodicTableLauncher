package ru.byters.periodictablelauncher.controllers;

import android.support.annotation.Nullable;

import ru.byters.periodictablelauncher.R;
import ru.byters.periodictablelauncher.models.ModelPreference;

public class ControllerPreference {

    private static ControllerPreference instance;
    @Nullable
    private ModelPreference model;

    private ControllerPreference() {
    }

    public static ControllerPreference getInstance() {
        if (instance == null) instance = new ControllerPreference();
        return instance;
    }

    void init() {
        model = Core.getInstance().readPreferenceCache();
    }

    public void storeData() {
        Core.getInstance().storePreferenceCache(model);
    }

    private boolean isColorIconDefaultSetted() {
        return model != null && model.getColorAppIcon() != ModelPreference.NO_VALUE;
    }

    public int getColorIconDefault() {
        return !isColorIconDefaultSetted()
                ? Core.getInstance().getResources().getColor(R.color.text_color)
                : model.getColorAppIcon();
    }

    public void setColorIconDefault(int color) {
        if (model == null) model = new ModelPreference();
        model.setColorAppIcon(color);
    }

    public void setAppShadowVisibility(boolean visibility) {
        if (model == null) model = new ModelPreference();
        model.setShadowVisible(visibility);
    }

    public boolean isShadowVisible() {
        return model != null && model.isShadowVisible();
    }

    public int getAppListOrientation() {
        return model == null ? ModelPreference.NO_VALUE : model.getAppListOrientation();
    }

    public void setAppListOrientation(int orientation) {
        if (model == null) model = new ModelPreference();
        model.setAppListOrientation(orientation);
    }

    public int getSortMethod() {
        return model == null ? ModelPreference.NO_VALUE : model.getSortMethod();
    }

    public void setSortMethod(int sortMethod) {
        if (model == null) model = new ModelPreference();
        model.setSortMethod(sortMethod);
    }

    public int getSortOrientation() {
        return model == null ? ModelPreference.NO_VALUE : model.getSortOrientation();
    }

    public void setSortOrientation(int sortOrientation) {
        if (model == null) model = new ModelPreference();
        model.setSortOrientation(sortOrientation);
    }
}
