package org.byters.periodictablelauncher.ui;

import android.view.View;

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

    public void notifyListeners(ListenerNavigation.NavigationType navigationType, View view, View view2) {
        if (listeners == null) return;
        for (ListenerNavigation item : listeners)
            item.onNavigate(navigationType, view, view2);
    }
}
