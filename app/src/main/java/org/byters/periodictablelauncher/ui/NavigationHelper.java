package org.byters.periodictablelauncher.ui;

import android.view.View;

public class NavigationHelper extends NavigationHelperBase {
    private static NavigationHelper instance;

    public static NavigationHelper getInstance() {
        if (instance == null) instance = new NavigationHelper();
        return instance;
    }

    public void navigateItemInfo(View view1, View view2) {
        notifyListeners(ListenerNavigation.NavigationType.TYPE_ITEM_INFO, view1, view2);
    }

    public void navigateItems() {
        notifyListeners(ListenerNavigation.NavigationType.TYPE_ITEMS, null, null);
    }

    public void navigateItemEdit(View view, View view2) {
        notifyListeners(ListenerNavigation.NavigationType.TYPE_ITEM_EDIT, view, view2);
    }
}
