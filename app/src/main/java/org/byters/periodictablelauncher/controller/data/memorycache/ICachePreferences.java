package org.byters.periodictablelauncher.controller.data.memorycache;

import org.byters.periodictablelauncher.controller.data.memorycache.callback.ICachePreferencesListener;

public interface ICachePreferences {
    int getColorIconDefault();

    void setColorIconDefault(int color);

    boolean isShadowEnabled();

    void storeData();

    int getSortOrientation();

    void setSortOrientation(int sortOrientation);

    int getSortMethod();

    void setSortMethod(int sortMethod);

    int getAppListOrientation();

    void setAppListOrientation(int orientation);

    void setAppShadowVisibility(boolean isChecked);

    void addListener(ICachePreferencesListener listener);
}
