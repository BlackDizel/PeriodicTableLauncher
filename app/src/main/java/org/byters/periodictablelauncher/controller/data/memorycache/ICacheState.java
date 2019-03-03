package org.byters.periodictablelauncher.controller.data.memorycache;

import org.byters.periodictablelauncher.controller.data.memorycache.callback.ICacheStateListener;

public interface ICacheState {
    void setScroll(float v);

    float getScroll();

    void notifyWallpaperChange();

    void addListener(ICacheStateListener listener);

    boolean isSettingsOpened();

    void setSettingsOpened(boolean isOpenedNewState);
}
