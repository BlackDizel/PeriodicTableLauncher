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

    public boolean isColorIconDefaultSetted() {
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
}
