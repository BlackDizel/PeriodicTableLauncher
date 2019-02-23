package org.byters.periodictablelauncher.controller.data.memorycache;

import org.byters.periodictablelauncher.controller.data.memorycache.callback.ICacheAppsListener;

import java.util.WeakHashMap;

abstract class CacheAppsBase implements ICacheApps {
    private WeakHashMap<String, ICacheAppsListener> listeners;

    @Override
    public void addListener(ICacheAppsListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }

    @Override
    public void removeListener(ICacheAppsListener listener) {
        if (listeners == null) return;
        listeners.remove(listener);
    }

    protected void notifyListeners() {
        if (listeners == null) return;
        for (String key: listeners.keySet())
            listeners.get(key).onUpdate();
    }
}
