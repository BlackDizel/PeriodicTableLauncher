package org.byters.periodictablelauncher.controller.data.memorycache;

import android.app.Application;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.model.AppDetail;
import org.byters.periodictablelauncher.model.FileEnum;
import org.byters.periodictablelauncher.view.util.HelperAppsCompare;
import org.byters.periodictablelauncher.view.util.IHelperStorage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

public class CacheApps extends CacheAppsBase implements ICacheApps {

    private static final String PACKAGE = "package";

    @Inject
    IHelperStorage helperStorage;

    @Inject
    HelperAppsCompare helperAppsCompare;

    @Inject
    WeakReference<Application> refApp;

    @Nullable
    private ArrayList<AppDetail> data;

    @Nullable
    private AppDetail selectedItem;

    public CacheApps() {
        ApplicationLauncher.getComponent().inject(this);
    }

    @Nullable
    private AppDetail getItem(int position) {
        if (data == null || position < 0 || position >= data.size())
            return null;
        return data.get(position);
    }

    public int getSize() {
        return data == null ? 0 : data.size();
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
        notifyListeners();
    }

    public boolean isSelectedItemExist() {
        return selectedItem != null;
    }

    public Uri getSelectedItemUri() {
        return selectedItem == null ? null : Uri.fromParts(PACKAGE, selectedItem.getPackageName(), null);
    }

    public int getSelectedItemColor() {
        return selectedItem == null ? AppDetail.NO_VALUE : selectedItem.getColor();
    }

    public void setSelectedItemColor(int color) {
        if (selectedItem == null) return;
        selectedItem.setColor(color);
        notifyListeners();
    }

    @Override
    public String getSelectedItemFullname() {
        return selectedItem == null ? null : selectedItem.getFullName();
    }

    @Override
    public boolean isDataExist() {
        return data != null && data.size() > 0;
    }

    @Override
    public ArrayList<AppDetail> getData() {
        return data;
    }

    public void setData(@Nullable ArrayList<AppDetail> result) {
        this.data = result;
        notifyListeners();
    }

    @Override
    public void reloadDataCurrent() {
        if (data == null) return;
        Collections.sort(data, helperAppsCompare.getComparator());
    }

    public void storeData() {
        helperStorage.writeData(data, FileEnum.CACHE_APPS);
    }

    @Override
    public int getItemsNum() {
        return data == null ? 0 : data.size();
    }

    @Override
    public String getItemFullname(int position) {
        AppDetail item = getItem(position);
        return item == null ? null : item.getFullName();
    }

    @Override
    public String getItemTitle(int position) {
        AppDetail item = getItem(position);
        return item == null ? null : item.getTitle();
    }

    @Override
    public String getItemLabel(int position) {
        AppDetail item = getItem(position);
        return item == null ? null : item.getLabel();
    }

    @Override
    public boolean isItemColorSetted(int position) {
        AppDetail item = getItem(position);
        return item != null && item.isColorSetted();
    }

    @Override
    public int getItemColor(int position) {
        AppDetail item = getItem(position);
        return item == null ? Color.WHITE : item.getColor();
    }

    @Override
    public String getItemPackageName(int position) {
        AppDetail item = getItem(position);
        return item == null ? null : item.getPackageName();
    }

    @Override
    public String getItemActivityName(int position) {
        AppDetail item = getItem(position);
        return item == null ? null : item.getActviityName();
    }

    @Override
    public void setSelectedItem(int position) {
        selectedItem = getItem(position);
    }

    public void removeCache() {
        data = null;
        selectedItem = null;
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
            item.resetCustomLabel(refApp.get());
        storeData();
        notifyListeners();
    }

    public void setSelectedItem(@Nullable AppDetail item) {
        selectedItem = item;
    }
}
