package org.byters.periodictablelauncher.ui.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.controllers.ControllerItems;
import org.byters.periodictablelauncher.controllers.ControllerPreference;
import org.byters.periodictablelauncher.controllers.Core;
import org.byters.periodictablelauncher.controllers.ListenerAppsUpdate;
import org.byters.periodictablelauncher.models.ModelPreference;
import org.byters.periodictablelauncher.ui.adapters.ItemsAdapter;
import org.byters.periodictablelauncher.view.presenter.IPresenterWallpaper;

import java.lang.ref.WeakReference;

public class FragmentIcons extends FragmentBase
        implements ListenerAppsUpdate {

    private RecyclerView rvView;
    private RecyclerViewScrollListener scrollListener;
    private WeakReference<IPresenterWallpaper> refPresenterWallpaper;

    public static Fragment getFragment() {
        return new FragmentIcons();
    }

    @Override
    public void onStart() {
        super.onStart();
        ControllerItems.getInstance().addListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        ControllerItems.getInstance().removeListener(this);
    }

    @Override
    public void onAppsUpdate() {
        if (rvView == null) return;
        rvView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scrollListener = new RecyclerViewScrollListener();

        refPresenterWallpaper = new WeakReference<>(Core.getInstance().getInjector().getPresenterWallpaper());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_icons, container, false);
        setGrid(v);
        return v;
    }

    private void setGrid(View v) {
        rvView = (RecyclerView) v.findViewById(R.id.rvItems);
        rvView.setAdapter(new ItemsAdapter());
        setLayoutManager();
        rvView.addOnScrollListener(scrollListener);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLayoutManager();
    }

    private void setLayoutManager() {
        int orientation = ControllerPreference.getInstance().getAppListOrientation() == ModelPreference.ORIENTATION_HORIZONTAL
                ? GridLayoutManager.HORIZONTAL
                : GridLayoutManager.VERTICAL;

        rvView.setLayoutManager(new GridLayoutManager(getContext()
                , getResources().getInteger(R.integer.columns)
                , orientation
                , false));
    }

    private class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            refPresenterWallpaper.get().updateWallpaperPosition(rvView);
        }
    }
}
