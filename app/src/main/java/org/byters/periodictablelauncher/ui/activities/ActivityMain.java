package org.byters.periodictablelauncher.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.transition.ChangeBounds;
import android.support.transition.ChangeClipBounds;
import android.support.transition.ChangeTransform;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

    public static final String SHARED_ELEMENT_VIEW_NAME = "shared_view";
    public static final String SHARED_ELEMENT_VIEW_NAME_2 = "shared_view_2";
    private ImageView imageView;
    private WallpaperViewHelper wallpaperViewHelper;
    private WeakReference<IPresenterWallpaper> refPresenterWallpaper;
    private CallbackWallpaperPresenter callbackWallpaperPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        imageView.addOnLayoutChangeListener(new BackgroundLayoutChangeListener());

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

        Fragment fragment = FragmentItemInfo.getFragment();
        setTransitionsShared(fragment);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.flContent, fragment)
                .commit();
    }

    private void setFragmentItemEdit(View view, View view2) {

        Fragment fragment = FragmentItemEdit.getFragment();
        setTransitionsShared(fragment);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .addSharedElement(view, SHARED_ELEMENT_VIEW_NAME)
                .addSharedElement(view2, SHARED_ELEMENT_VIEW_NAME_2)
                .replace(R.id.flContent, fragment)
                .commit();
    }

    private void setTransitionsShared(Fragment fragment) {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeTransform());
        transitionSet.addTransition(new ChangeClipBounds());

        fragment.setSharedElementEnterTransition(transitionSet);
        fragment.setSharedElementReturnTransition(transitionSet);
    }

    @Override
    public void onWallpaperChange() {
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
    public void onNavigate(NavigationType type, View view, View view2) {
        if (type == NavigationType.TYPE_ITEM_INFO)
            setFragmentItemInfo();
        else if (type == NavigationType.TYPE_ITEMS)
            setFragmentGrid();
        else if (type == NavigationType.TYPE_ITEM_EDIT)
            setFragmentItemEdit(view, view2);
    }

    private class CallbackWallpaperPresenter implements PresenterWallpaperCallback {
        @Override
        public void updateWallpaper(RecyclerView recyclerView) {
            wallpaperViewHelper.updateWallpaperPosition(imageView, recyclerView);
        }
    }

    private class BackgroundLayoutChangeListener implements View.OnLayoutChangeListener {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            wallpaperViewHelper.updateWallpaperPosition(imageView, null);
        }
    }
}
