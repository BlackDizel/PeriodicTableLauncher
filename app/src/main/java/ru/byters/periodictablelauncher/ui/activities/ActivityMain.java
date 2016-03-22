package ru.byters.periodictablelauncher.ui.activities;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import ru.byters.periodictablelauncher.R;
import ru.byters.periodictablelauncher.ui.adapters.ItemsAdapter;

public class ActivityMain extends AppCompatActivity {
    private RecyclerView rvView;
    private ImageView imageView;
    private int imageWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        rvView = (RecyclerView) findViewById(R.id.rvItems);
        rvView.setAdapter(new ItemsAdapter(this));
        rvView.addItemDecoration(new AppItemDecoration(this));
        setLayoutManager();
        setBackground();
    }

    private void setBackground() {
        Drawable wallpaperDrawable = WallpaperManager.getInstance(this).getDrawable();
        if (wallpaperDrawable != null) {
            imageWidth = wallpaperDrawable.getIntrinsicWidth();
            imageView.setImageDrawable(wallpaperDrawable);

            rvView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    int imageContainerWidth = imageView.getWidth();
                    if (imageWidth > imageContainerWidth) {
                        float ratio = rvView.computeHorizontalScrollOffset() / (float) rvView.computeHorizontalScrollRange();
                        int pos = -(int) ((imageWidth - imageView.getWidth()) * ratio);
                        Matrix m = new Matrix();
                        m.reset();
                        m.postTranslate(pos, 0);
                        imageView.setImageMatrix(m);
                    }
                }
            });
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLayoutManager();
    }

    private void setLayoutManager() {
        rvView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.columns), GridLayoutManager.HORIZONTAL, false));
    }

    private class AppItemDecoration extends RecyclerView.ItemDecoration {
        int margin;

        public AppItemDecoration(Context context) {
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
}
