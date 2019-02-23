package org.byters.periodictablelauncher.view.presenter;

import android.support.v4.app.FragmentManager;

import org.byters.periodictablelauncher.view.ui.activities.ActivityBase;

public interface IPresenterActivityMain {
    void onCreate(ActivityBase activity, FragmentManager fragmentManager, int viewId, int flBackground);

    void onStop();

    void onStart(ActivityBase activity, FragmentManager fragmentManager, int viewId, int flBackground);
}
