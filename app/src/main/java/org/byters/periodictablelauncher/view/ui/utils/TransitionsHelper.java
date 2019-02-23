package org.byters.periodictablelauncher.view.ui.utils;

import android.support.v4.view.ViewCompat;
import android.view.View;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.controller.data.memorycache.ICacheApps;

import javax.inject.Inject;

public class TransitionsHelper {
    private static final String SHARED_ELEMENT_VIEW_NAME = "shared_view";
    private static final String SHARED_ELEMENT_VIEW_NAME_2 = "shared_view_2";

    @Inject
    ICacheApps cacheApps;

    public TransitionsHelper() {
        ApplicationLauncher.getComponent().inject(this);
    }

    public void setTransitionView1(String title, View view) {
        ViewCompat.setTransitionName(view, getViewName(SHARED_ELEMENT_VIEW_NAME, title));
    }

    private String getViewName(String name, String title) {
        return name + "_" + title;
    }

    public void setTransitionView2(String title, View view) {
        ViewCompat.setTransitionName(view, getViewName(SHARED_ELEMENT_VIEW_NAME_2, title));
    }

    public String getViewName1() {
        return getViewName(SHARED_ELEMENT_VIEW_NAME, cacheApps.getSelectedItemFullname());
    }

    public String getViewName2() {
        return getViewName(SHARED_ELEMENT_VIEW_NAME_2, cacheApps.getSelectedItemFullname());
    }
}
