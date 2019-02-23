package org.byters.periodictablelauncher.controller.data.memorycache;

import org.byters.periodictablelauncher.controller.data.memorycache.callback.ICacheStateListener;

import java.util.WeakHashMap;

public class CacheState implements ICacheState {
    private float scroll;
    private WeakHashMap<String, ICacheStateListener> listeners;

    @Override
    public float getScroll() {
        return scroll;
    }

    @Override
    public void setScroll(float v) {
        this.scroll = v;
        notifyListenersScroll();
    }

    @Override
    public void notifyWallpaperChange() {

        if (listeners == null) return;
        for (String key : listeners.keySet())
            listeners.get(key).onWallpaperChange();
    }

    private void notifyListenersScroll() {

        if (listeners == null) return;
        for (String key : listeners.keySet())
            listeners.get(key).onScroll();
    }

    @Override
    public void addListener(ICacheStateListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }
}
