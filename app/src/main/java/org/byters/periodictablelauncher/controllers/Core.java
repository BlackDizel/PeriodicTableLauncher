package org.byters.periodictablelauncher.controllers;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.models.AppDetail;
import org.byters.periodictablelauncher.models.ModelPreference;

public class Core extends Application {

    private static Core instance;

    public static Core getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ControllerPreference.getInstance().init();
    }

    private Intent getLauncherIntent(String name) {
        return getPackageManager().getLaunchIntentForPackage(name);
    }

    public void storeData(ArrayList<AppDetail> data) {
        ControllerStorage.storeCache(this, data);
    }

    @Nullable
    public void updateApps() {
        new UpdateAppsAsync().execute();
    }

    @Nullable
    private synchronized ArrayList<AppDetail> leftJoin(ArrayList<AppDetail> left, HashMap<String, AppDetail> right) {
        if (left == null || right == null) return left;

        ArrayList<AppDetail> result = new ArrayList<>();
        for (AppDetail itemLeft : left) {
            String name = itemLeft.getName();
            AppDetail item = right.containsKey(name) ? right.get(name) : itemLeft;
            result.add(item);
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

            long time = System.currentTimeMillis();
            try {
                time = getPackageManager().getPackageInfo(ri.activityInfo.packageName, PackageManager.GET_CONFIGURATIONS).firstInstallTime;
            } catch (PackageManager.NameNotFoundException e) {
            }

            app.setDate(time);
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

    public void startActivitySetWallpaper(Context context) {
        Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
        context.startActivity(Intent.createChooser(intent, getString(R.string.wallpaper_choose_dialog_title)));
    }

    @Nullable
    ModelPreference readPreferenceCache() {
        return ControllerStorage.readPreferenceCache(this);
    }

    void storePreferenceCache(ModelPreference model) {
        ControllerStorage.storePreferenceCache(this, model);
    }

    private class UpdateAppsAsync extends AsyncTask<Void, Void, ArrayList<AppDetail>> {

        @Override
        protected ArrayList<AppDetail> doInBackground(Void... params) {
            if (Core.getInstance() == null) return null;
            HashMap<String, AppDetail> result = ControllerStorage.getAppsCache(Core.getInstance());
            if (result == null) return getAppsInstalled();
            return leftJoin(getAppsInstalled(), result);
        }

        @Override
        protected void onPostExecute(ArrayList<AppDetail> result) {
            super.onPostExecute(result);
            ControllerItems.getInstance().setData(result);
        }
    }
}
