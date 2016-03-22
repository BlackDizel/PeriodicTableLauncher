package ru.byters.periodictablelauncher.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.byters.periodictablelauncher.R;
import ru.byters.periodictablelauncher.models.AppDetail;

public class ControllerItems {
    @Nullable
    private static ControllerItems instance;
    private Context context;
    @Nullable
    private ArrayList<AppDetail> data;
    @Nullable
    private PackageManager packageManager;

    private ControllerItems() {
    }

    public static void init(Context context) {
        if (instance == null)
            instance = new ControllerItems();
        instance.setContext(context.getApplicationContext());
    }

    public static int getSize(Context context) {
        if (instance == null)
            init(context);
        if (instance.data == null) instance.initData();
        return instance.data.size();
    }

    public static Intent getLauncherIntent(Context context, String name) {
        if (instance == null) init(context);
        return instance.packageManager.getLaunchIntentForPackage(name);
    }

    @Nullable
    public static AppDetail getItem(Context context, int position) {
        if (instance == null) init(context);
        if (instance.data == null) instance.initData();
        if (position < 0 || position >= instance.data.size())
            return null;
        return instance.data.get(position);
    }

    private void initData() {
        data = new ArrayList<>();

        //Intent intent = new Intent(Intent.ACTION_MAIN, null);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = packageManager.queryIntentActivities(intent, 0);
        for (ResolveInfo ri : availableActivities) {
            AppDetail app = new AppDetail();
            app.setName(ri.activityInfo.packageName);
            app.setLabel(ri.loadLabel(packageManager).toString());
            app.setTitle(getTitle(app.getLabel()));
            data.add(app);
        }

        Collections.sort(data);
    }

    private String getTitle(String label) {
        String buffer = label.toLowerCase().replaceAll(context.getString(R.string.vowels_eng), "");
        buffer = buffer.replaceAll(" ", "");
        buffer = buffer.replaceAll(context.getString(R.string.vowels_rus), "");
        buffer = buffer.substring(0, Math.min(2, buffer.length()));
        if (buffer.length() > 0)
            buffer = Character.toUpperCase(buffer.charAt(0)) + buffer.substring(1);
        return buffer;
    }

    private void setContext(Context context) {
        this.context = context;
        this.packageManager = context.getPackageManager();
    }
}
