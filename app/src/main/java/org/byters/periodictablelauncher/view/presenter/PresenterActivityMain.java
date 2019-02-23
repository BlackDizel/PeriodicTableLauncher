package org.byters.periodictablelauncher.view.presenter;

import android.support.v4.app.FragmentManager;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.controller.data.memorycache.ICacheApps;
import org.byters.periodictablelauncher.controller.data.memorycache.ICachePreferences;
import org.byters.periodictablelauncher.view.INavigator;
import org.byters.periodictablelauncher.view.ui.activities.ActivityBase;

import javax.inject.Inject;

public class PresenterActivityMain implements IPresenterActivityMain {

    @Inject
    INavigator navigator;

    @Inject
    ICacheApps cacheApps;

    @Inject
    ICachePreferences cachePreferences;

    public PresenterActivityMain() {
        ApplicationLauncher.getComponent().inject(this);
    }

    @Override
    public void onCreate(ActivityBase activity, FragmentManager fragmentManager, int viewId, int flBackground) {
        navigator.set(activity, fragmentManager, viewId, flBackground);
        navigator.navigateBackground();
        navigator.navigateApps();
    }

    @Override
    public void onStop() {
        cacheApps.storeData();
        cachePreferences.storeData();
    }

    @Override
    public void onStart(ActivityBase activity, FragmentManager fragmentManager, int viewId, int flBackground) {
        navigator.set(activity, fragmentManager, viewId, flBackground);
    }
}
