package org.byters.periodictablelauncher.controller.data.memorycache;

public interface ICachePreferences {
    int getColorIconDefault();

    boolean isShadowEnabled();

    void storeData();

    int getSortOrientation();

    int getSortMethod();

    int getAppListOrientation();

    void setAppShadowVisibility(boolean isChecked);

    void setColorIconDefault(int color);

    void setAppListOrientation(int orientation);

    void setSortMethod(int sortMethod);

    void setSortOrientation(int sortOrientation);
}
