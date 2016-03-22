package ru.byters.periodictablelauncher.ui.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.byters.periodictablelauncher.R;
import ru.byters.periodictablelauncher.ui.adapters.ItemsAdapter;

public class ActivityMain extends AppCompatActivity {
    private RecyclerView rvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rvView = (RecyclerView) findViewById(R.id.rvItems);
        rvView.setAdapter(new ItemsAdapter(this));
        rvView.addItemDecoration(new AppItemDecoration(this));
        setLayoutManager();

/*        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();

        View rootView = findViewById(R.id.rootView);
        if (wallpaperDrawable != null && rootView != null)
            rootView.setBackground(wallpaperDrawable);*/
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
