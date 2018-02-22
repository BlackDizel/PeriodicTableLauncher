package org.byters.periodictablelauncher.controllers;

import android.support.v4.view.ViewCompat;
import android.view.View;

import org.byters.periodictablelauncher.models.AppDetail;

public class TransitionsHelper {
    private static final String SHARED_ELEMENT_VIEW_NAME = "shared_view";
    private static final String SHARED_ELEMENT_VIEW_NAME_2 = "shared_view_2";


    private static TransitionsHelper instance;

    public static TransitionsHelper getInstance() {
        if (instance == null) instance = new TransitionsHelper();
        return instance;
    }

    public static void setTransitionView1(AppDetail item, View view) {
        ViewCompat.setTransitionName(view, getViewName(SHARED_ELEMENT_VIEW_NAME, item));
    }

    private static String getViewName(String name, AppDetail item) {
        return name + "_" + item.getFullName();
    }

    public static void setTransitionView2(AppDetail item, View view) {
        ViewCompat.setTransitionName(view, getViewName(SHARED_ELEMENT_VIEW_NAME_2, item));
    }

    public static String getViewName1() {
        AppDetail item = ControllerItems.getInstance().getSelectedItem();
        if (item == null) return null;

        return getViewName(SHARED_ELEMENT_VIEW_NAME, item);
    }

    public static String getViewName2() {
        AppDetail item = ControllerItems.getInstance().getSelectedItem();
        if (item == null) return null;

        return getViewName(SHARED_ELEMENT_VIEW_NAME_2, item);
    }
}
