package org.byters.periodictablelauncher.controllers;

import android.net.Uri;
import android.support.annotation.Nullable;

import org.byters.periodictablelauncher.models.AppDetail;

import java.util.ArrayList;

public class ControllerItems extends ControllerItemsBase {

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

    @Nullable
    public AppDetail getItem(int position) {
        if (data == null || position < 0 || position >= data.size())
            return null;
        return data.get(position);
    }

    public int getSize() {
        return data == null ? 0 : data.size();
    }

    public void updateData() {
        Core.getInstance().updateApps();
    }

    @Nullable
    public String getSelectedItemLabel() {
        return selectedItem == null ? null : selectedItem.getLabel();
    }

    @Nullable
    public String getSelectedItemTitle() {
        return selectedItem == null ? null : selectedItem.getTitle();
    }

    public void setSelectedItemTitle(String title) {
        if (selectedItem == null) return;
        selectedItem.setTitle(title);
    }

    public boolean isSelectedItemExist() {
        return selectedItem != null;
    }

    public Uri getSelectedItemUri() {
        return selectedItem == null ? null : Uri.fromParts(PACKAGE, selectedItem.getPackageName(), null);
    }

    public void setSelectedItem(@Nullable AppDetail item) {
        selectedItem = item;
    }

    public int getSelectedItemColor() {
        return selectedItem == null ? AppDetail.NO_VALUE : selectedItem.getColor();
    }

    public void setSelectedItemColor(int color) {
        if (selectedItem == null) return;
        selectedItem.setColor(color);
    }

    public void storeData() {
        Core.getInstance().storeData(data);
    }

    public void setData(@Nullable ArrayList<AppDetail> result) {
        this.data = result;
        notifyListeners();
    }

    public void removeCache() {
        data = null;
        storeData();
        notifyListeners();
    }

    public void resetAppIconsCustomColor() {
        if (data == null) return;
        for (AppDetail item : data)
            item.resetColor();
        storeData();
        notifyListeners();
    }

    public void resetAppIconsCustomLabels() {
        if (data == null) return;
        for (AppDetail item : data)
            item.resetCustomLabel();
        storeData();
        notifyListeners();
    }
}
