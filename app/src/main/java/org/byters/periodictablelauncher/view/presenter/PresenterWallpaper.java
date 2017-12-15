package org.byters.periodictablelauncher.view.presenter;


import android.support.v7.widget.RecyclerView;

import org.byters.periodictablelauncher.view.presenter.callback.PresenterWallpaperCallback;

import java.lang.ref.WeakReference;

public class PresenterWallpaper implements IPresenterWallpaper {
    private WeakReference<PresenterWallpaperCallback> refCallback;

    @Override
    public void updateWallpaperPosition(RecyclerView recyclerView) {
        if (refCallback == null || refCallback.get() == null) return;
        refCallback.get().updateWallpaper(recyclerView);
    }

    @Override
    public void setCallback(PresenterWallpaperCallback callback) {
        this.refCallback = new WeakReference<>(callback);
    }
}
