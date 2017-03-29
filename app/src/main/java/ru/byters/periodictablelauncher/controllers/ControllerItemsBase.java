package ru.byters.periodictablelauncher.controllers;

import java.util.ArrayList;

class ControllerItemsBase {
    private ArrayList<ListenerAppsUpdate> listeners;

    public void addListener(ListenerAppsUpdate listener) {
        if (listeners == null) listeners = new ArrayList<>();
        listeners.add(listener);
    }

    public void removeListener(ListenerAppsUpdate listener) {
        if (listeners == null) return;
        listeners.remove(listener);
    }

    protected void notifyListeners() {
        if (listeners == null) return;
        for (ListenerAppsUpdate item : listeners)
            item.onAppsUpdate();
    }
}
