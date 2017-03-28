package ru.byters.periodictablelauncher.controllers;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import ru.byters.periodictablelauncher.models.AppDetail;

public class ControllerItems {

    private static ControllerItems instance;

    @Nullable
    private ArrayList<AppDetail> data;

    private ControllerItems() {
    }

    public static ControllerItems getInstance() {
        if (instance == null)
            instance = new ControllerItems();
        return instance;
    }

    public void init() {
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
}
