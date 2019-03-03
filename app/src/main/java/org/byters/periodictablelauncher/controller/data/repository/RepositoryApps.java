package org.byters.periodictablelauncher.controller.data.repository;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.controller.data.memorycache.ICacheApps;
import org.byters.periodictablelauncher.model.AppDetail;
import org.byters.periodictablelauncher.model.AppsCache;
import org.byters.periodictablelauncher.model.FileEnum;
import org.byters.periodictablelauncher.view.util.HelperAppsCompare;
import org.byters.periodictablelauncher.view.util.IHelperStorage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class RepositoryApps implements IRepositoryApps {

    @Inject
    ICacheApps cacheApps;

    @Inject
    IHelperStorage helperStorage;

    @Inject
    WeakReference<Application> refApplication;


    @Inject
    HelperAppsCompare helperAppsCompare;

    private UpdateAppsAsync request;

    public RepositoryApps() {
        ApplicationLauncher.getComponent().inject(this);
    }

    @Override
    public void request() {
        if (request != null)
            request.cancel(true);

        request = new UpdateAppsAsync(refApplication, helperStorage, cacheApps, helperAppsCompare);

        request.execute();
    }

    private static class UpdateAppsAsync extends AsyncTask<Void, Void, ArrayList<AppDetail>> {

        private final WeakReference<Application> refApp;
        private final ICacheApps cacheApps;
        private final IHelperStorage helperStorage;
        private final HelperAppsCompare comparator;

        UpdateAppsAsync(WeakReference<Application> refApp, IHelperStorage helperStorage, ICacheApps cacheApps, HelperAppsCompare comparator) {
            this.refApp = refApp;
            this.cacheApps = cacheApps;
            this.helperStorage = helperStorage;
            this.comparator = comparator;
        }

        @Override
        protected ArrayList<AppDetail> doInBackground(Void... params) {

            ArrayList<AppDetail> appsInstalled = getAppsInstalled();

            if (cacheApps.isDataExist())
                return leftJoin(appsInstalled, cacheApps.getData());

            AppsCache cache = helperStorage.readFile(FileEnum.CACHE_APPS, AppsCache.class);
            HashMap<String, AppDetail> result = cache == null ? null : cache.getData();
            if (result == null) return appsInstalled;

            return leftJoin(appsInstalled, result);
        }

        @Override
        protected void onPostExecute(ArrayList<AppDetail> result) {
            super.onPostExecute(result);
            cacheApps.setData(result);
        }

        @Nullable
        private ArrayList<AppDetail> getAppsInstalled() {
            ArrayList<AppDetail> result = null;
            Application application = refApp == null ? null : refApp.get();
            if (application == null) return null;

            PackageManager pm = application.getPackageManager();

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> availableActivities = pm.queryIntentActivities(intent, 0);
            for (ResolveInfo ri : availableActivities) {
                AppDetail app = new AppDetail();
                app.setPackageName(ri.activityInfo.packageName);

                app.setActivityName(ri.activityInfo.name);
                app.setFullName(ri.activityInfo.packageName + "_" + ri.activityInfo.name);

                long time = System.currentTimeMillis();
                try {
                    time = pm.getPackageInfo(ri.activityInfo.packageName, PackageManager.GET_CONFIGURATIONS).firstInstallTime;
                } catch (PackageManager.NameNotFoundException e) {
                }

                app.setDate(time);
                app.setLabel(ri.loadLabel(pm).toString());
                app.resetCustomLabel(refApp.get());
                if (result == null) result = new ArrayList<>();
                result.add(app);
            }

            if (result == null) return null;

            Collections.sort(result, comparator.getComparator());
            return result;
        }

        @Nullable
        private ArrayList<AppDetail> leftJoin(ArrayList<AppDetail> left, HashMap<String, AppDetail> right) {
            if (left == null || right == null) return left;

            ArrayList<AppDetail> result = new ArrayList<>(left.size());
            for (AppDetail itemLeft : left) {
                String name = itemLeft.getFullName();
                AppDetail item = right.containsKey(name) ? right.get(name) : itemLeft;
                result.add(item);
            }
            return result;
        }

        @Nullable
        private ArrayList<AppDetail> leftJoin(ArrayList<AppDetail> left, ArrayList<AppDetail> right) {
            if (left == null || right == null) return left;

            ArrayList<AppDetail> result = new ArrayList<>(left.size());
            for (AppDetail itemLeft : left) {
                String name = itemLeft.getFullName();
                AppDetail item = getItem(right, name);
                result.add(item != null ? item : itemLeft);
            }
            return result;
        }

        private AppDetail getItem(ArrayList<AppDetail> list, String name) {
            if (list == null || TextUtils.isEmpty(name)) return null;
            for (AppDetail item : list)
                if (name.equals(item.getFullName()))
                    return item;
            return null;
        }
    }


}
