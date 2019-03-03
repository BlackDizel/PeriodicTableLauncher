package org.byters.periodictablelauncher.view.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.transition.ChangeTransform;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.Toast;

import org.byters.periodictablelauncher.BuildConfig;
import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.view.INavigator;
import org.byters.periodictablelauncher.view.ui.activities.ActivityBase;
import org.byters.periodictablelauncher.view.ui.fragments.FragmentApps;
import org.byters.periodictablelauncher.view.ui.fragments.FragmentBackground;
import org.byters.periodictablelauncher.view.ui.fragments.FragmentItemEdit;
import org.byters.periodictablelauncher.view.ui.fragments.FragmentItemInfo;

import java.lang.ref.WeakReference;

public class Navigator implements INavigator {

    private WeakReference<ActivityBase> refActivity;
    private WeakReference<FragmentManager> refFragmentManager;
    private int viewId;
    private int viewIdBackground;

    public void startActivity(String packageName, String name) {
        if (refActivity == null || refActivity.get() == null) return;
        Intent intent = getLauncherIntent(packageName, name);
        refActivity.get().startActivity(intent);
    }

    @Override
    public void startActivitySetWallpaper() {
        if (refActivity == null || refActivity.get() == null) return;
        Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
        refActivity.get().startActivity(Intent.createChooser(intent, refActivity.get().getString(R.string.wallpaper_choose_dialog_title)));
    }

    private Intent getLauncherIntent(String packageName, String name) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, name));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public void openPlayMarketPage() {
        if (refActivity == null || refActivity.get() == null) return;
        openUrl(refActivity.get().getString(R.string.browser_error),
                Uri.parse(String.format(refActivity.get().getString(R.string.play_market_address_format), BuildConfig.APPLICATION_ID)));
    }

    private void openUrl(String error_message, Uri uri) {
        if (refActivity == null || refActivity.get() == null) return;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        if (intent.resolveActivity(refActivity.get().getPackageManager()) != null) {
            refActivity.get().startActivity(intent);
        } else {
            Toast.makeText(refActivity.get(), error_message, Toast.LENGTH_SHORT).show();
        }
    }

    public void sendSupportRequest() {
        if (refActivity == null || refActivity.get() == null) return;
        Intent intentSend = getIntentSendEmail(refActivity.get()
                , R.string.feedback_message_title
                , R.string.feedback_message_body);

        if (intentSend.resolveActivity(refActivity.get().getPackageManager()) == null) {
            Toast.makeText(refActivity.get(), R.string.email_app_error_no_found, Toast.LENGTH_SHORT).show();
            return;
        }

        refActivity.get().startActivity(Intent.createChooser(intentSend, ""));
    }

    @Override
    public void startActivityAppDetails(Uri uri) {
        if (refActivity == null || refActivity.get() == null) return;
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
        refActivity.get().startActivity(intent);
    }

    @Override
    public void startActivityAppRemove(Uri uri) {
        if (refActivity == null || refActivity.get() == null) return;
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        refActivity.get().startActivity(intent);
    }

    @Override
    public void navigateItemEdit(View view1, View view2) {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;

        Fragment fragment = FragmentItemEdit.getFragment();
        setTransitionsShared(fragment);

        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .addSharedElement(view1, ViewCompat.getTransitionName(view1))
                .addSharedElement(view2, ViewCompat.getTransitionName(view2))
                .replace(viewId, fragment)
                .commit();
    }

    @NonNull
    public Intent getIntentSendEmail(Context context, @StringRes int titleRes, @StringRes int bodyRes) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(context.getString(R.string.request_buy_email)));
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(titleRes));
        intent.putExtra(Intent.EXTRA_TEXT, context.getString(bodyRes));
        return intent;
    }

    @Override
    public void navigateItemInfo(View view1, View view2) {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;

        Fragment fragment = FragmentItemInfo.getFragment();
        setTransitionsShared(fragment);

        refFragmentManager.get()
                .beginTransaction()
                .addToBackStack(null)
                .addSharedElement(view1, ViewCompat.getTransitionName(view1))
                .addSharedElement(view2, ViewCompat.getTransitionName(view2))
                .replace(viewId, fragment)
                .commit();
    }

    @Override
    public void navigateBackground() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;

        refFragmentManager.get()
                .beginTransaction()
                .replace(viewIdBackground, new FragmentBackground())
                .commit();
    }

    @Override
    public void navigateApps() {
        if (refFragmentManager == null || refFragmentManager.get() == null) return;

        refFragmentManager.get()
                .beginTransaction()
                .replace(viewId, new FragmentApps())
                .commit();
    }

    private void setTransitionsShared(Fragment fragment) {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeTransform());

        fragment.setSharedElementEnterTransition(transitionSet);
        fragment.setSharedElementReturnTransition(transitionSet);
    }

    @Override
    public void set(ActivityBase activity, FragmentManager fragmentManager, int viewId, int viewIdBackground) {
        this.refActivity = new WeakReference<>(activity);
        this.refFragmentManager = new WeakReference<>(fragmentManager);
        this.viewId = viewId;
        this.viewIdBackground = viewIdBackground;
    }
}
