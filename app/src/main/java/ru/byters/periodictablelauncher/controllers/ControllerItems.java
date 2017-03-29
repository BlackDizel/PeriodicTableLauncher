package ru.byters.periodictablelauncher.controllers;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import ru.byters.periodictablelauncher.models.AppDetail;

public class ControllerItems {

    private static final String PACKAGE = "package";
    private static ControllerItems instance;
    @Nullable
    private ArrayList<AppDetail> data;
    @Nullable
    private AppDetail selectedItem;

    private ControllerItems() {
        selectedItem = null;
        data = null;
    }

    public static ControllerItems getInstance() {
        if (instance == null)
            instance = new ControllerItems();
        return instance;
    }

    void init() {
        updateData();
    }

    @Nullable
    public AppDetail getItem(int position) {
        if (data == null || position < 0 || position >= data.size())
            return null;
        return data.get(position);
    }

    public int getSize() {
        return data == null ? 0 : data.size();
    }

    private void updateData() {
        data = Core.getInstance().getApps();
    }

    @Nullable
    public String getSelectedItemLabel() {
        return selectedItem == null ? null : selectedItem.getLabel();
    }

    @Nullable
    public String getSelectedItemTitle() {
        return selectedItem == null ? null : selectedItem.getTitle();
    }

    public boolean isSelectedItemExist() {
        return selectedItem != null;
    }

    public Uri getSelectedItemUri() {
        return selectedItem == null ? null : Uri.fromParts(PACKAGE, selectedItem.getName(), null);
    }

    public void setSelectedItem(@Nullable AppDetail item) {
        selectedItem = item;
    }
}
