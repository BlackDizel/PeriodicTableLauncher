package org.byters.periodictablelauncher.ui;

public interface ListenerNavigation {

    void onNavigate(NavigationType type);

    enum NavigationType {
        TYPE_ITEMS, TYPE_ITEM_EDIT, TYPE_ITEM_INFO
    }
}
