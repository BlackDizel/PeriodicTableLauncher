package org.byters.periodictablelauncher.controller.data.memorycache;

import android.app.Application;
import android.graphics.Color;
import android.support.annotation.Nullable;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.model.FileEnum;
import org.byters.periodictablelauncher.model.ModelPreference;
import org.byters.periodictablelauncher.view.util.IHelperStorage;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class CachePreferences implements ICachePreferences {

    @Inject
    IHelperStorage helperStorage;

    @Inject
    WeakReference<Application> refApp;

    private ModelPreference model;

    public CachePreferences() {
        ApplicationLauncher.getComponent().inject(this);
    }

    @Nullable
    private ModelPreference readPreferenceCache() {
        return helperStorage.readFile(FileEnum.CACHE_PREFERENCES, ModelPreference.class);
    }

    void storePreferenceCache(ModelPreference model) {
        helperStorage.writeData(model, FileEnum.CACHE_PREFERENCES);
    }

    @Override
    public void storeData() {
        helperStorage.writeData(model, FileEnum.CACHE_PREFERENCES);
    }

    private boolean isColorIconDefaultSetted() {
        return checkData().getColorAppIcon() != ModelPreference.NO_VALUE;
    }

    private ModelPreference checkData() {
        if (model == null)
            model = readPreferenceCache();
        if (model == null)
            model = new ModelPreference();
        return model;
    }

    public int getColorIconDefault() {
        return isColorIconDefaultSetted()
                ? checkData().getColorAppIcon()
                : refApp != null && refApp.get() != null
                ? refApp.get().getResources().getColor(R.color.text_color)
                : Color.WHITE;
    }

    public void setColorIconDefault(int color) {
        checkData().setColorAppIcon(color);
    }

    @Override
    public boolean isShadowEnabled() {
        return checkData().isShadowVisible();
    }

    public void setAppShadowVisibility(boolean visibility) {
        checkData().setShadowVisible(visibility);
    }

    public int getAppListOrientation() {
        return checkData().getAppListOrientation();
    }

    public void setAppListOrientation(int orientation) {
        checkData().setAppListOrientation(orientation);
    }

    public int getSortMethod() {
        return checkData().getSortMethod();
    }

    public void setSortMethod(int sortMethod) {
        checkData().setSortMethod(sortMethod);
    }

    public int getSortOrientation() {
        return checkData().getSortOrientation();
    }

    public void setSortOrientation(int sortOrientation) {
        checkData().setSortOrientation(sortOrientation);
    }
}