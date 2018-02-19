package org.byters.periodictablelauncher.ui;

import android.view.View;

public interface ListenerNavigation {

    void onNavigate(NavigationType type, View view, View view2);

    enum NavigationType {
        TYPE_ITEMS, TYPE_ITEM_EDIT, TYPE_ITEM_INFO
    }
}
