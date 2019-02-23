package org.byters.periodictablelauncher.view.presenter;

import android.app.Application;
import android.app.WallpaperManager;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.byters.periodictablelauncher.ApplicationLauncher;
import org.byters.periodictablelauncher.controller.data.memorycache.ICacheState;
import org.byters.periodictablelauncher.controller.data.memorycache.callback.ICacheStateListener;
import org.byters.periodictablelauncher.view.presenter.callback.IPresenterFragmentBackgroundListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterFragmentBackground implements IPresenterFragmentBackground {

    private final ListenerCacheState listenerCacheState;

    @Inject
    ICacheState cacheState;

    @Inject
    WeakReference<Application> refApp;

    private Matrix m;

    private WeakReference<IPresenterFragmentBackgroundListener> refListener;

    private WeakReference<ImageView> refImageView;

    public PresenterFragmentBackground() {
        ApplicationLauncher.getComponent().inject(this);
        m = new Matrix();
        cacheState.addListener(listenerCacheState = new ListenerCacheState());
    }

    @Override
    public void setListener(IPresenterFragmentBackgroundListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onCreateView(ImageView imageView) {
        this.refImageView = new WeakReference<>(imageView);
        setWallpaper();
    }

    @Override
    public void onImageLayoutChange() {
        updateWallpaperPosition();
    }

    private void setWallpaper() {
        Drawable wallpaperDrawable = WallpaperManager.getInstance(refApp.get()).getDrawable();
        if (wallpaperDrawable == null) return;
        setViewDrawable(wallpaperDrawable);

        updateWallpaperPosition();
    }

    private void setViewDrawable(Drawable wallpaperDrawable) {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setBackground(wallpaperDrawable);
    }

    private void updateWallpaperPosition() {

        ImageView imageView = refImageView == null ? null : refImageView.get();
        if (imageView == null) return;
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
            float ratio = cacheState.getScroll();
            int pos = -(int) ((imageWidth - imageView.getWidth()) * ratio);
            m.postTranslate(pos, 0);
        }

        imageView.setImageMatrix(m);
    }

    private class ListenerCacheState implements ICacheStateListener {
        @Override
        public void onWallpaperChange() {
            setWallpaper();
        }

        @Override
        public void onScroll() {
            updateWallpaperPosition();
        }
    }
}
