package org.byters.periodictablelauncher.ui;

public class NavigationHelper extends NavigationHelperBase {
    private static NavigationHelper instance;

    public static NavigationHelper getInstance() {
        if (instance == null) instance = new NavigationHelper();
        return instance;
    }

    public void navigateItemInfo() {
        norifyListeners(ListenerNavigation.NavigationType.TYPE_ITEM_INFO);
    }

    public void navigateItems() {
        norifyListeners(ListenerNavigation.NavigationType.TYPE_ITEMS);
    }

    public void navigateItemEdit() {
        norifyListeners(ListenerNavigation.NavigationType.TYPE_ITEM_EDIT);
    }
}
