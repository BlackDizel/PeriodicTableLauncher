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
import ru.byters.periodictablelauncher.ui.adapters.ItemsAdapter;

public class FragmentIcons extends FragmentBase {

    private RecyclerView rvView;
    private ImageView imageView;
    private RecyclerViewScrollListener scrollListener;

    public static Fragment getFragment() {
        return new FragmentIcons();
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
        setBackground(v);
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
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLayoutManager();
    }

    private void setLayoutManager() {
        rvView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.columns), GridLayoutManager.HORIZONTAL, false));
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
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int imageContainerWidth = imageView.getWidth();
            int imageWidth = imageView.getDrawable().getIntrinsicWidth();
            if (imageWidth > imageContainerWidth) {
                float ratio = rvView.computeHorizontalScrollOffset() / (float) rvView.computeHorizontalScrollRange();
                int pos = -(int) ((imageWidth - imageView.getWidth()) * ratio);
                Matrix m = new Matrix();
                m.reset();
                m.postTranslate(pos, 0);
                imageView.setImageMatrix(m);
            }
        }
    }
}
