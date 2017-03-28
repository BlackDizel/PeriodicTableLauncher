package ru.byters.periodictablelauncher.controllers;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
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
        ControllerItems.getInstance().init();
    }

    private Intent getLauncherIntent(String name) {
        return getPackageManager().getLaunchIntentForPackage(name);
    }

    public ArrayList<AppDetail> getApps() {

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
        buffer = buffer.substring(0, Math.min(2, buffer.length()));
        if (buffer.length() > 0)
            buffer = Character.toUpperCase(buffer.charAt(0)) + buffer.substring(1);

        if (TextUtils.isEmpty(buffer))
            buffer = "Nn";
        return buffer;
    }

    public void startActivity(String name) {
        Intent intent = getLauncherIntent(name);
        startActivity(intent);
    }
}