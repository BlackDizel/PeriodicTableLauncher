package org.byters.periodictablelauncher.controllers;

import java.util.ArrayList;

public class ControllerWallpaper {

    private static ControllerWallpaper instance;
    private ArrayList<ListenerWallpaperChange> listeners;

    public static ControllerWallpaper getInstance() {
        if (instance == null) instance = new ControllerWallpaper();
        return instance;
    }

    public void addListener(ListenerWallpaperChange listener) {
        if (listeners == null) listeners = new ArrayList<>();
        listeners.add(listener);
    }

    public void removeListener(ListenerWallpaperChange listener) {
        if (listeners == null) return;
        listeners.remove(listener);
    }

    void notifyListeners() {
        if (listeners == null) return;
        for (ListenerWallpaperChange listener : listeners)
            listener.onWallpaperChange();
    }
}
