package org.byters.periodictablelauncher.ui.fragments;

import android.app.WallpaperManager;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.byters.periodictablelauncher.R;
import org.byters.periodictablelauncher.controllers.ControllerItems;
import org.byters.periodictablelauncher.controllers.ControllerPreference;
import org.byters.periodictablelauncher.controllers.ControllerWallpaper;
import org.byters.periodictablelauncher.controllers.ListenerAppsUpdate;
import org.byters.periodictablelauncher.controllers.ListenerWallpaperChange;
import org.byters.periodictablelauncher.models.ModelPreference;
import org.byters.periodictablelauncher.ui.adapters.ItemsAdapter;

public class FragmentIcons extends FragmentBase
        implements ListenerAppsUpdate, ListenerWallpaperChange {

    private RecyclerView rvView;
    private ImageView imageView;
    private RecyclerViewScrollListener scrollListener;

    public static Fragment getFragment() {
        return new FragmentIcons();
    }

    @Override
    public void onStart() {
        super.onStart();
        ControllerItems.getInstance().addListener(this);
        ControllerWallpaper.getInstance().addListener(this);
        setBackground(getView());
    }

    @Override
    public void onStop() {
        super.onStop();
        ControllerItems.getInstance().removeListener(this);
        ControllerWallpaper.getInstance().removeListener(this);
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_icons, container, false);
        setGrid(v);
        setBackground(v);
        return v;
    }

    private void setGrid(View v) {
        rvView = (RecyclerView) v.findViewById(R.id.rvItems);
        rvView.setAdapter(new ItemsAdapter());
        setLayoutManager();
        rvView.addOnScrollListener(scrollListener);
    }

    private void setBackground(View v) {
        imageView = (ImageView) v.findViewById(R.id.imageView);
        Drawable wallpaperDrawable = WallpaperManager.getInstance(getContext()).getDrawable();
        if (wallpaperDrawable == null) return;
        imageView.setImageDrawable(wallpaperDrawable);
        updateWallpaperPosition(new Matrix());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setBackground(getView());
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

    @Override
    public void onWallpaperChange() {
        if (getView() == null) return;
        setBackground(getView());
    }

    private void updateWallpaperPosition(Matrix m) {
        if (imageView.getDrawable() == null) return;
        m.reset();

        float scale = imageView.getHeight() / (float) imageView.getDrawable().getIntrinsicHeight();
        m.postScale(scale, scale);

        int imageContainerWidth = imageView.getWidth();
        float imageWidth = imageView.getDrawable().getIntrinsicWidth() * scale;
        float xScale = imageContainerWidth / imageWidth;
        if (xScale > 1)
            m.postScale(xScale, xScale);

        if (xScale < 1) {
            float ratio = rvView.computeHorizontalScrollOffset() / (float) rvView.computeHorizontalScrollRange();
            int pos = -(int) ((imageWidth - imageView.getWidth()) * ratio);
            m.postTranslate(pos, 0);
        }

        imageView.setImageMatrix(m);
    }

    private class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {

        private final Matrix m;

        RecyclerViewScrollListener() {
            m = new Matrix();
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            updateWallpaperPosition(m);
        }
    }
}
