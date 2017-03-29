package ru.byters.periodictablelauncher.ui;

import java.util.ArrayList;

class NavigationHelperBase {
    private ArrayList<ListenerNavigation> listeners;

    public void addListener(ListenerNavigation listener) {
        if (listeners == null) listeners = new ArrayList<>();
        listeners.add(listener);
    }

    public void removeListener(ListenerNavigation listener) {
        if (listeners == null) return;
        listeners.remove(listener);
    }

    public void norifyListeners(ListenerNavigation.NavigationType navigationType) {
        if (listeners == null) return;
        for (ListenerNavigation item : listeners)
            item.onNavigate(navigationType);
    }
}
