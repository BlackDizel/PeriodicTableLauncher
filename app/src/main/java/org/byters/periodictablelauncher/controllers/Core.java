package org.byters.periodictablelauncher.controllers;

import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.byters.periodictablelauncher.BuildConfig;
import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.models.AppDetail;
import org.byters.periodictablelauncher.models.ModelPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Core extends Application {

    private static Core instance;

    public static Core getInstance() {
        return instance;
    }

    public static void hideKeyboard(Context context, IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, 0);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ControllerPreference.getInstance().init();
    }

    private Intent getLauncherIntent(String packageName, String name) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, name));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
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
            app.setPackageName(ri.activityInfo.packageName);
            app.setName(ri.activityInfo.name);

            long time = System.currentTimeMillis();
            try {
                time = getPackageManager().getPackageInfo(ri.activityInfo.packageName, PackageManager.GET_CONFIGURATIONS).firstInstallTime;
            } catch (PackageManager.NameNotFoundException e) {
            }

            app.setDate(time);
            app.setLabel(ri.loadLabel(getPackageManager()).toString());
            app.resetCustomLabel();
            if (result == null) result = new ArrayList<>();
            result.add(app);
        }

        if (result == null) return null;

        Collections.sort(result);
        return result;
    }

    public String getTitle(String label) {
        String buffer = label.toLowerCase().replaceAll(getString(R.string.vowels_eng), "");
        buffer = buffer.replaceAll(" ", "");
        buffer = buffer.replaceAll(getString(R.string.vowels_rus), "");
        buffer = buffer.substring(0, Math.min(AppDetail.MAX_ITEM_TITLE_LENGTH, buffer.length()));
        if (buffer.length() > 1)
            buffer = Character.toUpperCase(buffer.charAt(0)) + buffer.substring(1);

        //empty strings allowed
        return buffer;
    }

    public void startActivity(String packageName, String name) {
        Intent intent = getLauncherIntent(packageName, name);
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

    public void openPlayMarketPage(Context context) {
        openUrl(context, getString(R.string.browser_error), Uri.parse(String.format(getString(R.string.play_market_address_format), BuildConfig.APPLICATION_ID)));
    }

    private void openUrl(Context context, String error_message, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, error_message, Toast.LENGTH_SHORT).show();
        }
    }

    public void sendSupportRequest(Context context) {
        Intent intentSend = getIntentSendEmail(this
                , R.string.feedback_message_title
                , R.string.feedback_message_body);

        if (intentSend.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(context, R.string.email_app_error_no_found, Toast.LENGTH_SHORT).show();
            return;
        }
        context.startActivity(intentSend);
    }

    @NonNull
    public Intent getIntentSendEmail(Context context, @StringRes int titleRes, @StringRes int bodyRes) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(context.getString(R.string.request_buy_email)));
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(titleRes));
        intent.putExtra(Intent.EXTRA_TEXT, context.getString(bodyRes));
        return intent;
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
