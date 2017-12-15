package org.byters.periodictablelauncher.view.presenter;

import android.support.v7.widget.RecyclerView;

import org.byters.periodictablelauncher.view.presenter.callback.PresenterWallpaperCallback;

public interface IPresenterWallpaper {
    void updateWallpaperPosition(RecyclerView recyclerView);

    void setCallback(PresenterWallpaperCallback callback);
}
