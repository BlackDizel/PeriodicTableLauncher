package ru.byters.periodictablelauncher.ui;

public interface ListenerNavigation {

    public void onNavigate(NavigationType type);

    enum NavigationType {
        TYPE_ITEMS, TYPE_ITEM_INFO
    }
}
