package ru.byters.periodictablelauncher.controllers;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import ru.byters.periodictablelauncher.R;
import ru.byters.periodictablelauncher.models.AppDetail;

public class Core extends Application {

    private static Core instance;

    public static Core getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    private Intent getLauncherIntent(String name) {
        return getPackageManager().getLaunchIntentForPackage(name);
    }

    public void storeData(ArrayList<AppDetail> data) {
        ControllerStorage.storeCache(this, data);
    }

    @Nullable
    public synchronized ArrayList<AppDetail> getApps() {
        HashMap<String, AppDetail> result = ControllerStorage.getAppsCache(this);
        if (result == null) return getAppsInstalled();
        return leftJoin(getAppsInstalled(), result);
    }

    @Nullable
    private synchronized ArrayList<AppDetail> leftJoin(ArrayList<AppDetail> left, HashMap<String, AppDetail> right) {
        if (left == null || right == null) return left;

        ArrayList<AppDetail> result = new ArrayList<>();
        for (AppDetail itemLeft : left) {
            String name = itemLeft.getName();
            AppDetail item = right.containsKey(name) ? right.get(name) : itemLeft;
            if (!result.contains(item)) result.add(item);
        }
        return result;
    }

    @Nullable
    private ArrayList<AppDetail> getAppsInstalled() {
        ArrayList<AppDetail> result = null;

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo ri : availableActivities) {
            AppDetail app = new AppDetail();
            app.setName(ri.activityInfo.packageName);
            app.setLabel(ri.loadLabel(getPackageManager()).toString());
            app.setTitle(getTitle(app.getLabel()));
            if (result == null) result = new ArrayList<>();
            result.add(app);
        }

        if (result == null) return null;

        Collections.sort(result);
        return result;
    }

    private String getTitle(String label) {
        String buffer = label.toLowerCase().replaceAll(getString(R.string.vowels_eng), "");
        buffer = buffer.replaceAll(" ", "");
        buffer = buffer.replaceAll(getString(R.string.vowels_rus), "");
        buffer = buffer.substring(0, Math.min(AppDetail.MAX_ITEM_TITLE_LENGTH, buffer.length()));
        if (buffer.length() > 1)
            buffer = Character.toUpperCase(buffer.charAt(0)) + buffer.substring(1);

        //empty strings allowed
        return buffer;
    }

    public void startActivity(String name) {
        Intent intent = getLauncherIntent(name);
        startActivity(intent);
    }

    public void startActivityAppDetails(Context context, Uri uri) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
        context.startActivity(intent);
    }

    public void startActivityAppRemove(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        context.startActivity(intent);
    }
}
