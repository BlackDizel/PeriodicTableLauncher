package org.byters.periodictablelauncher.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.controllers.ControllerItems;
import org.byters.periodictablelauncher.controllers.ControllerPreference;
import org.byters.periodictablelauncher.controllers.ControllerWallpaper;
import org.byters.periodictablelauncher.controllers.Core;
import org.byters.periodictablelauncher.controllers.ListenerWallpaperChange;
import org.byters.periodictablelauncher.ui.NavigationHelper;
import org.byters.periodictablelauncher.ui.fragments.FragmentIcons;
import org.byters.periodictablelauncher.ui.fragments.FragmentItemEdit;
import org.byters.periodictablelauncher.ui.fragments.FragmentItemInfo;
import org.byters.periodictablelauncher.ui.utils.WallpaperViewHelper;
import org.byters.periodictablelauncher.view.presenter.IPresenterWallpaper;
import org.byters.periodictablelauncher.view.presenter.callback.PresenterWallpaperCallback;

import java.lang.ref.WeakReference;

public class ActivityMain extends ActivityBase
        implements ListenerWallpaperChange {

    private ImageView imageView;
    private WallpaperViewHelper wallpaperViewHelper;
    private WeakReference<IPresenterWallpaper> refPresenterWallpaper;
    private CallbackWallpaperPresenter callbackWallpaperPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);

        wallpaperViewHelper = new WallpaperViewHelper();

        refPresenterWallpaper = new WeakReference<>(Core.getInstance().getInjector().getPresenterWallpaper());
        callbackWallpaperPresenter = new CallbackWallpaperPresenter();

        setFragmentGrid();
    }

    private void setFragmentGrid() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, FragmentIcons.getFragment())
                .commit();
    }

    private void setFragmentItemInfo() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.flContent, FragmentItemInfo.getFragment())
                .commit();
    }

    @Override
    public void onWallpaperChange() {
        wallpaperViewHelper.setWallpaper(imageView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        wallpaperViewHelper.setWallpaper(imageView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        NavigationHelper.getInstance().addListener(this);
        ControllerItems.getInstance().updateData();

        ControllerWallpaper.getInstance().addListener(this);
        wallpaperViewHelper.setWallpaper(imageView);
        refPresenterWallpaper.get().setCallback(callbackWallpaperPresenter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NavigationHelper.getInstance().removeListener(this);
        ControllerItems.getInstance().storeData();
        ControllerPreference.getInstance().storeData();
        ControllerWallpaper.getInstance().removeListener(this);
    }

    @Override
    public void onNavigate(NavigationType type) {
        if (type == NavigationType.TYPE_ITEM_INFO)
            setFragmentItemInfo();
        else if (type == NavigationType.TYPE_ITEMS)
            setFragmentGrid();
        else if (type == NavigationType.TYPE_ITEM_EDIT)
            setFragmentItemEdit();
    }

    private void setFragmentItemEdit() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.flContent, FragmentItemEdit.getFragment())
                .commit();
    }

    private class CallbackWallpaperPresenter implements PresenterWallpaperCallback {
        @Override
        public void updateWallpaper(RecyclerView recyclerView) {
            wallpaperViewHelper.updateWallpaperPosition(imageView, recyclerView);
        }
    }
}
