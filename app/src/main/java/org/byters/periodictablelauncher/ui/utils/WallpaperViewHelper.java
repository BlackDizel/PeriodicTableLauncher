package org.byters.periodictablelauncher.ui.utils;

import android.app.WallpaperManager;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

public class WallpaperViewHelper {

    private final Matrix m;

    public WallpaperViewHelper() {
        m = new Matrix();
    }

    public void setBackground(ImageView imageView) {
        Drawable wallpaperDrawable = WallpaperManager.getInstance(imageView.getContext()).getDrawable();
        if (wallpaperDrawable == null) return;
        imageView.setImageDrawable(wallpaperDrawable);
        updateWallpaperPosition(imageView, null);
    }

    public void updateWallpaperPosition(ImageView imageView, @Nullable RecyclerView recyclerView) {
        if (imageView.getDrawable() == null) return;
        m.reset();

        float scale = imageView.getHeight() / (float) imageView.getDrawable().getIntrinsicHeight();
        m.postScale(scale, scale);

        int imageContainerWidth = imageView.getWidth();
        float imageWidth = imageView.getDrawable().getIntrinsicWidth() * scale;
        float xScale = imageContainerWidth / imageWidth;
        if (xScale > 1)
            m.postScale(xScale, xScale);

        if (xScale < 1 && recyclerView != null) {
            float ratio = recyclerView.computeHorizontalScrollOffset() / (float) recyclerView.computeHorizontalScrollRange();
            int pos = -(int) ((imageWidth - imageView.getWidth()) * ratio);
            m.postTranslate(pos, 0);
        }

        imageView.setImageMatrix(m);
    }
}
