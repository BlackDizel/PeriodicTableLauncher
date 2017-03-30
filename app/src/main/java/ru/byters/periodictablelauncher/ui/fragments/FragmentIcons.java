package ru.byters.periodictablelauncher.ui.fragments;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.Rect;
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

import ru.byters.periodictablelauncher.R;
import ru.byters.periodictablelauncher.controllers.ControllerItems;
import ru.byters.periodictablelauncher.controllers.ControllerWallpaper;
import ru.byters.periodictablelauncher.controllers.ListenerAppsUpdate;
import ru.byters.periodictablelauncher.controllers.ListenerWallpaperChange;
import ru.byters.periodictablelauncher.ui.adapters.ItemsAdapter;

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
        return v;
    }

    private void setGrid(View v) {
        rvView = (RecyclerView) v.findViewById(R.id.rvItems);
        rvView.setAdapter(new ItemsAdapter());
        rvView.addItemDecoration(new AppItemDecoration(getContext()));
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
        setLayoutManager();
    }

    private void setLayoutManager() {
        rvView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.columns), GridLayoutManager.HORIZONTAL, false));
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
        int imageWidth = (int) (imageView.getDrawable().getIntrinsicWidth() * scale);
        if (imageWidth > imageContainerWidth) {
            float ratio = rvView.computeHorizontalScrollOffset() / (float) rvView.computeHorizontalScrollRange();
            int pos = -(int) ((imageWidth - imageView.getWidth()) * ratio);
            m.postTranslate(pos, 0);
        }

        imageView.setImageMatrix(m);
    }

    private class AppItemDecoration extends RecyclerView.ItemDecoration {
        int margin;

        AppItemDecoration(Context context) {
            margin = (int) context.getResources().getDimension(R.dimen.item_margin);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            //parent.getChildLayoutPosition(view);

            outRect.top = margin;
            outRect.left = margin;
            outRect.right = margin;
            outRect.bottom = margin;

        }
    }

    private class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {

        private final Matrix m;

        RecyclerViewScrollListener() {
            m = new Matrix();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            updateWallpaperPosition(m);
        }
    }
}
